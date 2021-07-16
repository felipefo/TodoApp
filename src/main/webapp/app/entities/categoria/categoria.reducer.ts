import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICategoria, defaultValue } from 'app/shared/model/categoria.model';

export const ACTION_TYPES = {
  FETCH_CATEGORIA_LIST: 'categoria/FETCH_CATEGORIA_LIST',
  FETCH_CATEGORIA: 'categoria/FETCH_CATEGORIA',
  CREATE_CATEGORIA: 'categoria/CREATE_CATEGORIA',
  UPDATE_CATEGORIA: 'categoria/UPDATE_CATEGORIA',
  PARTIAL_UPDATE_CATEGORIA: 'categoria/PARTIAL_UPDATE_CATEGORIA',
  DELETE_CATEGORIA: 'categoria/DELETE_CATEGORIA',
  RESET: 'categoria/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICategoria>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CategoriaState = Readonly<typeof initialState>;

// Reducer

export default (state: CategoriaState = initialState, action): CategoriaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CATEGORIA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CATEGORIA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CATEGORIA):
    case REQUEST(ACTION_TYPES.UPDATE_CATEGORIA):
    case REQUEST(ACTION_TYPES.DELETE_CATEGORIA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CATEGORIA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CATEGORIA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CATEGORIA):
    case FAILURE(ACTION_TYPES.CREATE_CATEGORIA):
    case FAILURE(ACTION_TYPES.UPDATE_CATEGORIA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CATEGORIA):
    case FAILURE(ACTION_TYPES.DELETE_CATEGORIA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CATEGORIA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CATEGORIA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CATEGORIA):
    case SUCCESS(ACTION_TYPES.UPDATE_CATEGORIA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CATEGORIA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CATEGORIA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/categorias';

// Actions

export const getEntities: ICrudGetAllAction<ICategoria> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CATEGORIA_LIST,
    payload: axios.get<ICategoria>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICategoria> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CATEGORIA,
    payload: axios.get<ICategoria>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICategoria> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CATEGORIA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICategoria> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CATEGORIA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICategoria> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CATEGORIA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICategoria> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CATEGORIA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
