import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDebtMySuffix, defaultValue } from 'app/shared/model/debt-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_DEBT_LIST: 'debt/FETCH_DEBT_LIST',
  FETCH_DEBT: 'debt/FETCH_DEBT',
  CREATE_DEBT: 'debt/CREATE_DEBT',
  UPDATE_DEBT: 'debt/UPDATE_DEBT',
  DELETE_DEBT: 'debt/DELETE_DEBT',
  RESET: 'debt/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDebtMySuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type DebtMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: DebtMySuffixState = initialState, action): DebtMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DEBT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DEBT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DEBT):
    case REQUEST(ACTION_TYPES.UPDATE_DEBT):
    case REQUEST(ACTION_TYPES.DELETE_DEBT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DEBT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DEBT):
    case FAILURE(ACTION_TYPES.CREATE_DEBT):
    case FAILURE(ACTION_TYPES.UPDATE_DEBT):
    case FAILURE(ACTION_TYPES.DELETE_DEBT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEBT_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEBT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DEBT):
    case SUCCESS(ACTION_TYPES.UPDATE_DEBT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DEBT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/debts';

// Actions

export const getEntities: ICrudGetAllAction<IDebtMySuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DEBT_LIST,
    payload: axios.get<IDebtMySuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IDebtMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DEBT,
    payload: axios.get<IDebtMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDebtMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DEBT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IDebtMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DEBT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDebtMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DEBT,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
