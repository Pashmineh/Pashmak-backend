import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EventMySuffix from './event-my-suffix';
import PaymentMySuffix from './payment-my-suffix';
import DebtMySuffix from './debt-my-suffix';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/event-my-suffix`} component={EventMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/payment-my-suffix`} component={PaymentMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/debt-my-suffix`} component={DebtMySuffix} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
