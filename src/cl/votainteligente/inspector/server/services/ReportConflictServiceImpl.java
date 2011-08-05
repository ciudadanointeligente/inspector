package cl.votainteligente.inspector.server.services;

import cl.votainteligente.inspector.client.services.ReportConflictService;
import cl.votainteligente.inspector.model.Parlamentarian;
import cl.votainteligente.inspector.model.ReportConflict;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;

public class ReportConflictServiceImpl implements ReportConflictService {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<ReportConflict> getAllReportConflicts() throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(ReportConflict.class);
			List<ReportConflict> reportConflicts = criteria.list();
			hibernate.getTransaction().commit();
			return reportConflicts;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public ReportConflict getReportConflict(Long reportConflictId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			ReportConflict reportConflict = (ReportConflict) hibernate.get(ReportConflict.class, reportConflictId);
			hibernate.getTransaction().commit();
			return reportConflict;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public ReportConflict saveReportConflict(ReportConflict reportConflict, Long parlamentarianId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			Parlamentarian parlamentarian = (Parlamentarian) hibernate.load(Parlamentarian.class, parlamentarianId);
			reportConflict.setParlamentarian(parlamentarian);
			reportConflict.setSubmitDate(new Date());
			hibernate.saveOrUpdate(reportConflict);
			hibernate.getTransaction().commit();
			return reportConflict;
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}

	@Override
	public void deleteReportConflict(Long reportConflictId) throws Exception {
		Session hibernate = sessionFactory.getCurrentSession();

		try {
			hibernate.beginTransaction();
			ReportConflict report = (ReportConflict) hibernate.get(ReportConflict.class, reportConflictId);
			hibernate.delete(report);
			hibernate.getTransaction().commit();
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			throw ex;
		}
	}
}
