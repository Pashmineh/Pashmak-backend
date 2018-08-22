import { Moment } from 'moment';

export const enum PaymentType {
  TAKHIR = 'TAKHIR',
  SHIRINI = 'SHIRINI',
  JALASE = 'JALASE'
}

export interface IDebtMySuffix {
  id?: number;
  amount?: number;
  paymentTime?: Moment;
  reason?: PaymentType;
}

export const defaultValue: Readonly<IDebtMySuffix> = {};
