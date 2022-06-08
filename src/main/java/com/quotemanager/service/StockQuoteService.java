package com.quotemanager.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quotemanager.model.StockQuote;

@Service
@Transactional
public class StockQuoteService implements ServiceModel<StockQuote, String> {

	@PersistenceContext
	EntityManager em;

	@Override
	public List<StockQuote> list() {
		return em.createQuery("SELECT q FROM StockQuote q", StockQuote.class).getResultList();
	}

	public Stream<StockQuote> stream() {
		return em.createQuery("SELECT q FROM StockQuote q", StockQuote.class).getResultStream();
	}

	@Override
	public Optional<StockQuote> find(String id) {
		return Optional.of(em.find(StockQuote.class, id));
	}

	public Optional<StockQuote> findByStockId(String stockId) {
		try {
			return Optional.of(em.createQuery("SELECT q FROM StockQuote q WHERE stockId = ?1", StockQuote.class)
					.setParameter(1, stockId).getSingleResult());
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<StockQuote> insert(StockQuote entity) {
//		entity.getQuotes().forEach(quote -> em.merge(quote));
		return Optional.of(em.merge(entity));
	}

	@Override
	public Optional<StockQuote> update(String id, StockQuote entity) {
		Optional<StockQuote> qOpt = Optional.of(em.find(StockQuote.class, id));
		StockQuote q = new StockQuote();
		if (qOpt.isPresent()) {
			q = qOpt.get();
			q.setStockId(entity.getStockId());
			q.setQuotes(entity.getQuotes());
//			entity.getQuotes().forEach(quote -> em.merge(quote));
		}
		return Optional.of(em.merge(q));
	}

	@Override
	public void delete(String id) {
		em.remove(em.find(StockQuote.class, id));
	}

}
