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

import { IPaymentMySuffix, defaultValue } from 'app/shared/model/payment-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_PAYMENT_LIST: 'payment/FETCH_PAYMENT_LIST',
  FETCH_PAYMENT: 'payment/FETCH_PAYMENT',
  CREATE_PAYMENT: 'payment/CREATE_PAYMENT',
  UPDATE_PAYMENT: 'payment/UPDATE_PAYMENT',
  DELETE_PAYMENT: 'payment/DELETE_PAYMENT',
  RESET: 'payment/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPaymentMySuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type PaymentMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: PaymentMySuffixState = initialState, action): PaymentMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PAYMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PAYMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PAYMENT):
    case REQUEST(ACTION_TYPES.UPDATE_PAYMENT):
    case REQUEST(ACTION_TYPES.DELETE_PAYMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PAYMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PAYMENT):
    case FAILURE(ACTION_TYPES.CREATE_PAYMENT):
    case FAILURE(ACTION_TYPES.UPDATE_PAYMENT):
    case FAILURE(ACTION_TYPES.DELETE_PAYMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAYMENT_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAYMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PAYMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_PAYMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PAYMENT):
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

const apiUrl = 'api/payments';

// Actions

export const getEntities: ICrudGetAllAction<IPaymentMySuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PAYMENT_LIST,
    payload: axios.get<IPaymentMySuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IPaymentMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PAYMENT,
    payload: axios.get<IPaymentMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPaymentMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PAYMENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IPaymentMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PAYMENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPaymentMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PAYMENT,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
