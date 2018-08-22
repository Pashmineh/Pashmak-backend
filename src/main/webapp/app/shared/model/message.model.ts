import { Moment } from 'moment';

export const enum MessageType {
  PUSH = 'PUSH',
  SMS = 'SMS',
  EMAIL = 'EMAIL'
}

export interface IMessage {
  id?: number;
  sendTime?: Moment;
  messageType?: MessageType;
  message?: string;
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<IMessage> = {};
