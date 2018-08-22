import { Moment } from 'moment';

export interface ICheckin {
  id?: number;
  checkinTime?: Moment;
  message?: string;
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<ICheckin> = {};
