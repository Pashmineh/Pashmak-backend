import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DebtMySuffix from './debt-my-suffix';
import DebtMySuffixDetail from './debt-my-suffix-detail';
import DebtMySuffixUpdate from './debt-my-suffix-update';
import DebtMySuffixDeleteDialog from './debt-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DebtMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DebtMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DebtMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={DebtMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DebtMySuffixDeleteDialog} />
  </>
);

export default Routes;
