package study.domain;

import cafe.domain.CafeId;
import common.DateAudit;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Study {

	private StudyId id;
	private StudyContent content;
	private CafeId cafeId;
	private Coordinator coordinator;
	private RecruitmentStatus recruitmentStatus;

	private DateAudit dateAudit;

	public boolean hasId(StudyId id) {
		return this.id.isSameId(id);
	}

	public boolean hasCafeId(CafeId id) {
		return this.cafeId.isSameId(id);
	}

	public boolean isManagedBy(Coordinator coordinator) {
		return this.coordinator.isSameCoordinator(coordinator);
	}
}
