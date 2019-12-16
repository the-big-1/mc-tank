package company18.mctank.repository;

import org.salespointframework.catalog.Catalog;
import org.salespointframework.catalog.Product;
import org.springframework.data.util.Streamable;

/**
 * Items CRUD Repository.
 */
public interface ItemsRepository extends Catalog<Product> {
}