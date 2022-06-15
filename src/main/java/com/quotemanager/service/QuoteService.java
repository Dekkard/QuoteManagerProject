package com.quotemanager.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quotemanager.model.Quote;

@Service
@Transactional
public class QuoteService implements ServiceModel<Quote, Long> {

	@PersistenceContext
	EntityManager em;

	@Override
	public List<Quote> list() {
		return em.createQuery("SELECT q FROM Quote q", Quote.class).getResultList();
	}

	public List<Quote> listByStockId(String stockId) {
		return em.createQuery("SELECT q FROM Quote WHERE stock_quote_id = ?1", Quote.class).setParameter(1, stockId)
				.getResultList();
	}

	@Override
	public Optional<Quote> find(Long id) {
		return Optional.of(em.find(Quote.class, id));
	}

	@Override
	public Optional<Quote> insert(Quote entity) {
		try {
			Optional<Quote> quote = Optional
					.of(em.createQuery("SELECT q FROM Quote q WHERE date = ?1 and stockQuote = ?2", Quote.class)//
							.setParameter(1, entity.getDate())//
							.setParameter(2, entity.getStockQuote())//
							.getSingleResult());
			if (quote.isPresent()) {
				Quote q = quote.get();
				q.setPrice(entity.getPrice());
				return Optional.of(em.merge(q));
			} else
				return Optional.of(em.merge(entity));
		} catch (Exception e) {
			return Optional.of(em.merge(entity));
		}
	}

	@Override
	public Optional<Quote> update(Long id, Quote entity) {
		Optional<Quote> qOpt = Optional.of(em.find(Quote.class, id));
		Quote q = new Quote();
		if (qOpt.isPresent()) {
			q = qOpt.get();
			q.setDate(entity.getDate());
			q.setPrice(entity.getPrice());
			q.setStockQuote(entity.getStockQuote());
		}
		return Optional.of(em.merge(q));
	}

	@Override
	public void delete(Long id) {
		em.remove(em.find(Quote.class, id));
	}

}
