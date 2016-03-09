package org.spring2885.server.db.model;

import org.spring2885.model.Job;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

public final class JobConverters {
	private static class FromDbToJson implements Function<DbJob, Job> {

		@Override
		public Job apply(DbJob db) {
			Job j = new Job();
			j.setId(db.getId());
			j.setTitle(db.getTitle());
			//j.setIndustry(db.getIndustry());
			j.setLocation(db.getLocation());
			j.setDescription(db.getDescription());
			//j.setjobType(db.getjobType());
			//j.setstartDate(db.getstartDate());
			//j.setendDate(db.getendDate());
			//j.setpostedbypersonId(db.getpostedbyPersonId());
			//j.setHours(db.getHours());

			return j;
		}
	}
	
	private static class FromJsonToDb implements Function<Job, DbJob> {
		private final Supplier<DbJob> dbSupplier;
		
		FromJsonToDb(Supplier<DbJob> dbSupplier) {
			this.dbSupplier = dbSupplier;
		}
		
		@Override
		public DbJob apply(Job p) {
			DbJob db = dbSupplier.get();
			// Leave the ID null since we're updating an existing person.
			db.setTitle(p.getTitle());
			//Integer jobType = p.getjobType();
			//if (jobType != null) {
			//	db.setjobType(p.getjobType());
			//} else {
			//	db.setjobType(0);
			//}
			db.setIndustry(db.getIndustry());
			db.setLocation(db.getLocation());
			db.setDescription(db.getDescription());
			db.setstartDate(db.getstartDate());
			db.setendDate(db.getendDate());
			
			
			return db;
		}
	}
	
	public static Function<DbJob, Job> fromDbToJson() {
		return new FromDbToJson();
	}
	
	public static Function<Job, DbJob> fromJsonToDb() {
		return fromJsonToDb(new Supplier<DbJob>() {
			@Override public DbJob get() {
				return new DbJob(); 
			} 
		});
	}
	
	public static Function<Job, DbJob> fromJsonToDb(Supplier<DbJob> dbPersonSupplier) {
		return new FromJsonToDb(dbPersonSupplier);
	}
	
	public static Function<Job, DbJob> fromJsonToDb(DbJob dbJob) {
		return new FromJsonToDb(Suppliers.ofInstance(dbJob));
	}
	
	private JobConverters() {}
}
