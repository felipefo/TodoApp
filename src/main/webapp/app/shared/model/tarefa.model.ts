import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ICategoria } from 'app/shared/model/categoria.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface ITarefa {
  id?: number;
  descricao?: string;
  dueDate?: string | null;
  dateCriacao?: string | null;
  status?: Status;
  user?: IUser | null;
  assigneed?: IUser | null;
  categoria?: ICategoria | null;
}

export const defaultValue: Readonly<ITarefa> = {};
