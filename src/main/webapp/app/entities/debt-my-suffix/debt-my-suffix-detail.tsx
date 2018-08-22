import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './debt-my-suffix.reducer';
import { IDebtMySuffix } from 'app/shared/model/debt-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDebtMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class DebtMySuffixDetail extends React.Component<IDebtMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { debtEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Debt [<b>{debtEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="amount">Amount</span>
            </dt>
            <dd>{debtEntity.amount}</dd>
            <dt>
              <span id="paymentTime">Payment Time</span>
            </dt>
            <dd>
              <TextFormat value={debtEntity.paymentTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="reason">Reason</span>
            </dt>
            <dd>{debtEntity.reason}</dd>
            <dt>User</dt>
            <dd>{debtEntity.userLogin ? debtEntity.userLogin : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/debt-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/debt-my-suffix/${debtEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ debt }: IRootState) => ({
  debtEntity: debt.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DebtMySuffixDetail);
