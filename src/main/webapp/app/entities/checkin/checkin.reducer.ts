import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICheckin, defaultValue } from 'app/shared/model/checkin.model';

export const ACTION_TYPES = {
  FETCH_CHECKIN_LIST: 'checkin/FETCH_CHECKIN_LIST',
  FETCH_CHECKIN: 'checkin/FETCH_CHECKIN',
  CREATE_CHECKIN: 'checkin/CREATE_CHECKIN',
  UPDATE_CHECKIN: 'checkin/UPDATE_CHECKIN',
  DELETE_CHECKIN: 'checkin/DELETE_CHECKIN',
  RESET: 'checkin/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICheckin>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type CheckinState = Readonly<typeof initialState>;

// Reducer

export default (state: CheckinState = initialState, action): CheckinState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CHECKIN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CHECKIN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CHECKIN):
    case REQUEST(ACTION_TYPES.UPDATE_CHECKIN):
    case REQUEST(ACTION_TYPES.DELETE_CHECKIN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CHECKIN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CHECKIN):
    case FAILURE(ACTION_TYPES.CREATE_CHECKIN):
    case FAILURE(ACTION_TYPES.UPDATE_CHECKIN):
    case FAILURE(ACTION_TYPES.DELETE_CHECKIN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CHECKIN_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CHECKIN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CHECKIN):
    case SUCCESS(ACTION_TYPES.UPDATE_CHECKIN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CHECKIN):
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

const apiUrl = 'api/checkins';

// Actions

export const getEntities: ICrudGetAllAction<ICheckin> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CHECKIN_LIST,
    payload: axios.get<ICheckin>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ICheckin> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CHECKIN,
    payload: axios.get<ICheckin>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICheckin> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CHECKIN,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICheckin> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CHECKIN,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICheckin> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CHECKIN,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
