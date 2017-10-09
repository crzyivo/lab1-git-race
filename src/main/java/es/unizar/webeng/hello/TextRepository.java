package es.unizar.webeng.hello;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/*
 * Interface that extends <code> CrudRepository </code> where the JPA entity Text will be stored.
 * Doing this, <code> TextRepository </code> will inherit several methods to work with Text.
 * The class that implements this interface will be created when <code>@Autowired</code> is used.
 */
@Repository
public interface TextRepository extends CrudRepository<Text, Long> { }

