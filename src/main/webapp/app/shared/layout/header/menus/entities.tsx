import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/event-my-suffix">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Event My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/payment-my-suffix">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Payment My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/debt-my-suffix">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Debt My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/message">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Message
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/checkin">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Checkin
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/payment-my-suffix">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Payment My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/debt-my-suffix">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Debt My Suffix
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
