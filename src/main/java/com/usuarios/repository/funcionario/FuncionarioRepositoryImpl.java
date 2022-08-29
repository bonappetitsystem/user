package com.usuarios.repository.funcionario;

import com.usuarios.entities.Funcionario;
import com.usuarios.entities.Funcionario_;
import com.usuarios.repository.projection.ResumoFuncionario;
import com.usuarios.repository.Filter.FuncionarioFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioRepositoryImpl implements FuncionarioRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;


    @Override
    public Page<Funcionario> filtrar(FuncionarioFilter funcionarioFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Funcionario> criteria = builder.createQuery(Funcionario.class);
        Root<Funcionario> root = criteria.from(Funcionario.class);
        Predicate[] predicates =  createRestrictions(funcionarioFilter, builder, root);
        criteria.where(predicates);
        TypedQuery<Funcionario> query = manager.createQuery(criteria);
        addRestrictionsPageable(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, total(funcionarioFilter));
    }

    @Override
    public Page<ResumoFuncionario> resumir(FuncionarioFilter funcionarioFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<ResumoFuncionario> criteria = builder.createQuery(ResumoFuncionario.class);
        Root<Funcionario> root = criteria.from(Funcionario.class);

        criteria.select(builder.construct(ResumoFuncionario.class,
                root.get(Funcionario_.ID),
                root.get(Funcionario_.NOME),
                root.get(Funcionario_.CPF),
                root.get(Funcionario_.GENERO).as(String.class),
                root.get(Funcionario_.DATA_NASCIMENTO),
                root.get(Funcionario_.PERFIL).as(String.class),
                root.get(Funcionario_.MATRICULA),
                root.get(Funcionario_.DATA_CADASTRO)));

        Predicate[] predicates =  createRestrictions(funcionarioFilter, builder, root);
        criteria.where(predicates);
        TypedQuery<ResumoFuncionario> query = manager.createQuery(criteria);
        addRestrictionsPageable(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, total(funcionarioFilter));
    }


    private Predicate [] createRestrictions(FuncionarioFilter funcionarioFilter, CriteriaBuilder builder, Root<Funcionario> root) {
        List<Predicate> predicates = new ArrayList<>();
        if(!ObjectUtils.isEmpty(funcionarioFilter.getCpf())){
            predicates.add(
                    builder.like(
                            builder.lower(root.get("cpf")), "%" + funcionarioFilter.getCpf().toLowerCase() + "%"
                    ));
        }

        if(!ObjectUtils.isEmpty(funcionarioFilter.getNome())){
            predicates.add(
                    builder.like(
                            builder.lower(root.get("nome")), "%" + funcionarioFilter.getNome().toLowerCase() + "%"
                    ));
        }

        if(!ObjectUtils.isEmpty(funcionarioFilter.getPerfil())){
            predicates.add(
                    builder.like(
                            builder.lower(root.get("perfil")), "%" + funcionarioFilter.getPerfil() + "%"
                    ));
        }

        return predicates.toArray(new Predicate[0]);
    }



    private void addRestrictionsPageable(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalDeRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalDeRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalDeRegistrosPorPagina);

    }

    private Long total(FuncionarioFilter funcionarioFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Funcionario> root = criteria.from(Funcionario.class);

        Predicate [] predicates = createRestrictions(funcionarioFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();

    }

}
