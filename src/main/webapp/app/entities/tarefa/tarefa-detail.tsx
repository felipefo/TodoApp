import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './tarefa.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITarefaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TarefaDetail = (props: ITarefaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { tarefaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tarefaDetailsHeading">
          <Translate contentKey="todo2App.tarefa.detail.title">Tarefa</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tarefaEntity.id}</dd>
          <dt>
            <span id="descricao">
              <Translate contentKey="todo2App.tarefa.descricao">Descricao</Translate>
            </span>
          </dt>
          <dd>{tarefaEntity.descricao}</dd>
          <dt>
            <span id="dueDate">
              <Translate contentKey="todo2App.tarefa.dueDate">Due Date</Translate>
            </span>
          </dt>
          <dd>{tarefaEntity.dueDate ? <TextFormat value={tarefaEntity.dueDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="dateCriacao">
              <Translate contentKey="todo2App.tarefa.dateCriacao">Date Criacao</Translate>
            </span>
          </dt>
          <dd>{tarefaEntity.dateCriacao ? <TextFormat value={tarefaEntity.dateCriacao} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="todo2App.tarefa.status">Status</Translate>
            </span>
          </dt>
          <dd>{tarefaEntity.status}</dd>
          <dt>
            <Translate contentKey="todo2App.tarefa.user">User</Translate>
          </dt>
          <dd>{tarefaEntity.user ? tarefaEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="todo2App.tarefa.assigneed">Assigneed</Translate>
          </dt>
          <dd>{tarefaEntity.assigneed ? tarefaEntity.assigneed.login : ''}</dd>
          <dt>
            <Translate contentKey="todo2App.tarefa.categoria">Categoria</Translate>
          </dt>
          <dd>{tarefaEntity.categoria ? tarefaEntity.categoria.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tarefa" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tarefa/${tarefaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ tarefa }: IRootState) => ({
  tarefaEntity: tarefa.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TarefaDetail);
