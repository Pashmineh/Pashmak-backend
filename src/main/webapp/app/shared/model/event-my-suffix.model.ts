import { Moment } from 'moment';

export interface IEventMySuffix {
  id?: number;
  location?: string;
  eventTime?: Moment;
  name?: string;
  description?: string;
}

export const defaultValue: Readonly<IEventMySuffix> = {};
