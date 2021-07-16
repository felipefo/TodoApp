import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITarefa, defaultValue } from 'app/shared/model/tarefa.model';

export const ACTION_TYPES = {
  FETCH_TAREFA_LIST: 'tarefa/FETCH_TAREFA_LIST',
  FETCH_TAREFA: 'tarefa/FETCH_TAREFA',
  CREATE_TAREFA: 'tarefa/CREATE_TAREFA',
  UPDATE_TAREFA: 'tarefa/UPDATE_TAREFA',
  PARTIAL_UPDATE_TAREFA: 'tarefa/PARTIAL_UPDATE_TAREFA',
  DELETE_TAREFA: 'tarefa/DELETE_TAREFA',
  RESET: 'tarefa/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITarefa>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TarefaState = Readonly<typeof initialState>;

// Reducer

export default (state: TarefaState = initialState, action): TarefaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TAREFA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TAREFA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TAREFA):
    case REQUEST(ACTION_TYPES.UPDATE_TAREFA):
    case REQUEST(ACTION_TYPES.DELETE_TAREFA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TAREFA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TAREFA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TAREFA):
    case FAILURE(ACTION_TYPES.CREATE_TAREFA):
    case FAILURE(ACTION_TYPES.UPDATE_TAREFA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TAREFA):
    case FAILURE(ACTION_TYPES.DELETE_TAREFA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TAREFA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TAREFA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TAREFA):
    case SUCCESS(ACTION_TYPES.UPDATE_TAREFA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TAREFA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TAREFA):
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

const apiUrl = 'api/tarefas';

// Actions

export const getEntities: ICrudGetAllAction<ITarefa> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TAREFA_LIST,
    payload: axios.get<ITarefa>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITarefa> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TAREFA,
    payload: axios.get<ITarefa>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITarefa> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TAREFA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITarefa> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TAREFA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITarefa> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TAREFA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITarefa> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TAREFA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
