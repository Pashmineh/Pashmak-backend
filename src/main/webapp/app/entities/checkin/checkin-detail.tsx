import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './checkin.reducer';
import { ICheckin } from 'app/shared/model/checkin.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICheckinDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class CheckinDetail extends React.Component<ICheckinDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { checkinEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Checkin [<b>{checkinEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="checkinTime">Checkin Time</span>
            </dt>
            <dd>
              <TextFormat value={checkinEntity.checkinTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="message">Message</span>
            </dt>
            <dd>{checkinEntity.message}</dd>
            <dt>User</dt>
            <dd>{checkinEntity.userLogin ? checkinEntity.userLogin : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/checkin" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/checkin/${checkinEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ checkin }: IRootState) => ({
  checkinEntity: checkin.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CheckinDetail);
