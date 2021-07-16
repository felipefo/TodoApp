import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Tarefa from './tarefa';
import TarefaDetail from './tarefa-detail';
import TarefaUpdate from './tarefa-update';
import TarefaDeleteDialog from './tarefa-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TarefaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TarefaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TarefaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Tarefa} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TarefaDeleteDialog} />
  </>
);

export default Routes;
