package com.devlog.post.repository;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.type.StandardBasicTypes;

public class MatchFunctionContributor implements FunctionContributor {
	@Override
	public void contributeFunctions(FunctionContributions functionContributions) {
		functionContributions.getFunctionRegistry().registerPattern(
			"MATCH_AGAINST_SINGLE",
			"MATCH (?1) AGAINST (?2)",
			functionContributions.getTypeConfiguration()
				.getBasicTypeRegistry()
				.resolve(StandardBasicTypes.DOUBLE)
		);
	}
}


