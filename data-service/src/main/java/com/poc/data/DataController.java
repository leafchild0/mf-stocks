package com.poc.data;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple controller to imitate some data which will be requested from client service
 */
@RestController
public class DataController {

	@GetMapping("/events")
	public List<DataDTO> getFullUserInfo() {

		List<DataDTO> events = new ArrayList<>();
		events.add(new DataDTO("created-01", "2020-02-24", "17:06", "New tracking has been created, first in the "
			+ "system"));
		events.add(new DataDTO("updated-01", "2020-02-25", "17:31", "Updated tracking due to changes in logistics"));
		events.add(new DataDTO("created-02", "2020-03-01", "11:31", "New tracking has been created, keep track of it"));
		events.add(new DataDTO("created-03", "2020-03-10", "12:22", "New tracking has been created, 3rd in the "
			+ "system"));
		events.add(new DataDTO("deleted-03", "2020-03-12", "04:01", "Removed tracking due to incorrect data in it"));
		events.add(new DataDTO("updated-02", "2020-03-14", "00:30", "Updated tracking because of changes in fiscal "
			+ "docs"));
		events.add(new DataDTO("updated-02.1", "2020-03-18", "17:30", "Updated tracking due to change location"));
		events.add(new DataDTO("deleted-01", "2020-03-25", "17:59", "Removed tracking due to inactivity for 1 month"));

		return events;
	}

	class DataDTO {

		private String name;
		private String date;
		private String time;
		private String message;

		public DataDTO(String name, String date, String time, String message) {

			this.name = name;
			this.date = date;
			this.time = time;
			this.message = message;
		}

		public String getName() {

			return name;
		}

		public String getDate() {

			return date;
		}

		public String getTime() {

			return time;
		}

		public String getMessage() {

			return message;
		}
	}
}
