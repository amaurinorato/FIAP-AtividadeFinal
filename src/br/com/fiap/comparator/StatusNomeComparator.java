package br.com.fiap.comparator;

import java.util.Comparator;

import twitter4j.Status;

public class StatusNomeComparator implements Comparator<Status> {

	@Override
	public int compare(Status status, Status outroStatus) {
		return status.getUser().getName().compareTo(outroStatus.getUser().getName());
	}

	
}
