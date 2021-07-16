import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './categoria.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICategoriaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CategoriaDetail = (props: ICategoriaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { categoriaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="categoriaDetailsHeading">
          <Translate contentKey="todo2App.categoria.detail.title">Categoria</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{categoriaEntity.id}</dd>
          <dt>
            <span id="descricao">
              <Translate contentKey="todo2App.categoria.descricao">Descricao</Translate>
            </span>
          </dt>
          <dd>{categoriaEntity.descricao}</dd>
          <dt>
            <Translate contentKey="todo2App.categoria.user">User</Translate>
          </dt>
          <dd>{categoriaEntity.user ? categoriaEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/categoria" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/categoria/${categoriaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ categoria }: IRootState) => ({
  categoriaEntity: categoria.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CategoriaDetail);
