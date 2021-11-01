package br.com.jcaguiar.ecommerce.service;

import br.com.jcaguiar.ecommerce.model.Perfil;
import br.com.jcaguiar.ecommerce.projection.MasterGET;
import br.com.jcaguiar.ecommerce.repository.PerfilRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfilService extends MasterService<Perfil, Integer> {


    public PerfilService(PerfilRepository jpaRepo) {
        super(jpaRepo);
    }

    public Perfil add(Perfil perfil) {
        return JPA_REPO.save(perfil);
    }

    @Override
    public List<? extends MasterGET> findTodos() {
        return null;
    }

    @Override
    public List<? extends MasterGET> findTodos(Sort ordene) {
        return null;
    }

    @Override
    public List<? extends MasterGET> findTodosAdm() {
        return null;
    }

    @Override
    public List<? extends MasterGET> findTodosAdm(Sort ordene) {
        return null;
    }

    @Override
    public MasterGET findId(Integer integer) {
        return null;
    }

    @Override
    public MasterGET findIdAdm(Integer integer) {
        return null;
    }

    @Override
    public MasterGET findEntidade(Perfil entidade) {
        return null;
    }

    @Override
    public MasterGET findEntidadeAdm(Perfil entidade) {
        return null;
    }

    @Override
    public List<? extends MasterGET> findByNome(String nome) {
        return null;
    }

    @Override
    public List<? extends MasterGET> findByNomeAdm(String nome) {
        return null;
    }

    @Override
    public List<? extends MasterGET> findByNomeContaining(String nome) {
        return null;
    }

    @Override
    public List<? extends MasterGET> findByNomeContainingAdm(String nome) {
        return null;
    }
}
