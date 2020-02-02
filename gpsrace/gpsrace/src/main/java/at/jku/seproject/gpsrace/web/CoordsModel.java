package at.jku.seproject.gpsrace.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordsModel {
	private double latitude;
	private double longitude;
}
