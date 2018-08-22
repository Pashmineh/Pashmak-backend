import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './debt-my-suffix.reducer';
import { IDebtMySuffix } from 'app/shared/model/debt-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDebtMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IDebtMySuffixUpdateState {
  isNew: boolean;
}

export class DebtMySuffixUpdate extends React.Component<IDebtMySuffixUpdateProps, IDebtMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    values.paymentTime = new Date(values.paymentTime);

    if (errors.length === 0) {
      const { debtEntity } = this.props;
      const entity = {
        ...debtEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/debt-my-suffix');
  };

  render() {
    const { debtEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="pashmakApp.debt.home.createOrEditLabel">Create or edit a Debt</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : debtEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="debt-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="amountLabel" for="amount">
                    Amount
                  </Label>
                  <AvField id="debt-my-suffix-amount" type="text" name="amount" />
                </AvGroup>
                <AvGroup>
                  <Label id="paymentTimeLabel" for="paymentTime">
                    Payment Time
                  </Label>
                  <AvInput
                    id="debt-my-suffix-paymentTime"
                    type="datetime-local"
                    className="form-control"
                    name="paymentTime"
                    value={isNew ? null : convertDateTimeFromServer(this.props.debtEntity.paymentTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="reasonLabel">Reason</Label>
                  <AvInput
                    id="debt-my-suffix-reason"
                    type="select"
                    className="form-control"
                    name="reason"
                    value={(!isNew && debtEntity.reason) || 'TAKHIR'}
                  >
                    <option value="TAKHIR">TAKHIR</option>
                    <option value="SHIRINI">SHIRINI</option>
                    <option value="JALASE">JALASE</option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/debt-my-suffix" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  debtEntity: storeState.debt.entity,
  loading: storeState.debt.loading,
  updating: storeState.debt.updating
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DebtMySuffixUpdate);
