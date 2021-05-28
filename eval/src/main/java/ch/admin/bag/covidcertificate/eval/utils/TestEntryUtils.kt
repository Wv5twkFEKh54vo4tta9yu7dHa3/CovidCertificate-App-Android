package ch.admin.bag.covidcertificate.eval.utils

import android.text.TextUtils
import ch.admin.bag.covidcertificate.eval.data.TestEntry
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


fun TestEntry.isNegative(): Boolean {
	return this.tr == AcceptanceCriterias.NEGATIVE_CODE
}

fun TestEntry.isTargetDiseaseCorrect(): Boolean {
	return this.tg == AcceptanceCriterias.TARGET_DISEASE
}

fun TestEntry.getFormattedSampleDate(dateTimeFormatter: DateTimeFormatter): String {
	return this.sc.toInstant().atZone(ZoneId.systemDefault()).format(dateTimeFormatter)
}

fun TestEntry.getTestCenter(): String? {
	if (!TextUtils.isEmpty(this.tc)) {
		return this.tc
	}
	return null
}

fun TestEntry.getTestCountry(): String {
	val loc = Locale("", this.co)
	return loc.displayCountry
}

fun TestEntry.getIssuer(): String {
	return this.`is`
}

fun TestEntry.getCertificateIdentifier(): String {
	return this.ci
}

fun TestEntry.validFromDate(): LocalDateTime? {
	return this.sc.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
}

fun TestEntry.validUntilDate(testEntry: TestEntry): LocalDateTime? {
	val startDate = this.validFromDate() ?: return null
	if (testEntry.tt.equals(TestType.PCR.code)) {
		return startDate.plusHours(AcceptanceCriterias.PCR_TEST_VALIDITY_IN_HOURS)
	} else if (testEntry.tt.equals(TestType.RAT.code)) {
		return startDate.plusHours(AcceptanceCriterias.RAT_TEST_VALIDITY_IN_HOURS)
	}
	return null
}

