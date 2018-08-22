import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PaymentMySuffix from './payment-my-suffix';
import PaymentMySuffixDetail from './payment-my-suffix-detail';
import PaymentMySuffixUpdate from './payment-my-suffix-update';
import PaymentMySuffixDeleteDialog from './payment-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PaymentMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PaymentMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PaymentMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={PaymentMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PaymentMySuffixDeleteDialog} />
  </>
);

export default Routes;
