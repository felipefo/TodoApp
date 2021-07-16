import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Categoria from './categoria';
import CategoriaDetail from './categoria-detail';
import CategoriaUpdate from './categoria-update';
import CategoriaDeleteDialog from './categoria-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CategoriaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CategoriaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CategoriaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Categoria} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CategoriaDeleteDialog} />
  </>
);

export default Routes;
