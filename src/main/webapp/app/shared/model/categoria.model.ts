import { ITarefa } from 'app/shared/model/tarefa.model';
import { IUser } from 'app/shared/model/user.model';

export interface ICategoria {
  id?: number;
  descricao?: string;
  tarefas?: ITarefa[] | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<ICategoria> = {};
