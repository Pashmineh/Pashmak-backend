import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Checkin from './checkin';
import CheckinDetail from './checkin-detail';
import CheckinUpdate from './checkin-update';
import CheckinDeleteDialog from './checkin-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CheckinUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CheckinUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CheckinDetail} />
      <ErrorBoundaryRoute path={match.url} component={Checkin} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CheckinDeleteDialog} />
  </>
);

export default Routes;
