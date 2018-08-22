import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import event, {
  EventMySuffixState
} from 'app/entities/event-my-suffix/event-my-suffix.reducer';
// prettier-ignore
import payment, {
  PaymentMySuffixState
} from 'app/entities/payment-my-suffix/payment-my-suffix.reducer';
// prettier-ignore
import debt, {
  DebtMySuffixState
} from 'app/entities/debt-my-suffix/debt-my-suffix.reducer';
// prettier-ignore
import message, {
  MessageState
} from 'app/entities/message/message.reducer';
// prettier-ignore
import checkin, {
  CheckinState
} from 'app/entities/checkin/checkin.reducer';
// prettier-ignore
import payment, {
  PaymentMySuffixState
} from 'app/entities/payment-my-suffix/payment-my-suffix.reducer';
// prettier-ignore
import debt, {
  DebtMySuffixState
} from 'app/entities/debt-my-suffix/debt-my-suffix.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly event: EventMySuffixState;
  readonly payment: PaymentMySuffixState;
  readonly debt: DebtMySuffixState;
  readonly message: MessageState;
  readonly checkin: CheckinState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  event,
  payment,
  debt,
  message,
  checkin,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
