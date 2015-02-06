var data_array_24 = [ ['Time', 'Memory (125 threads)', 'Memory (1 threads)', 'Memory (25 threads)', 'Memory (5 threads)' ] ];

var columns = [2, 2, 2, 2]; // 2, 2
var limiter = [jms_memory20150206185918.length, jms_memory20150206184935.length, jms_memory20150206185644.length, jms_memory20150206185259.length]; // arr.length, arrr.length
var data = [jms_memory20150206185918, jms_memory20150206184935, jms_memory20150206185644, jms_memory20150206185259];
var len = data.length;

var counter = [];
for (var i = 0; i < len; i++) {
	counter.push(1); // 0 is header
}

function countersUnderLimit() {
	var res = false;

	for (var i = 0; i < len; i++) {
		res = res || (counter[i] < limiter[i]);
	}

	return res;
}

function getTm(counter, dataArray) {
	if (counter < dataArray.length) {
	   var tmStr = dataArray[counter][0];
	   if (tmStr instanceof Date) {
	   	  return tmStr.getTime();
	   }
       return +tmStr;
	}

	return null;
}

var warmUpCol = [];
for (var i = 0; i < len; i++) {
	warmUpCol.push(data[i][0].indexOf('warmUp'));
}

function isWarmUp() {
	var res = false;

	for (var i = 0; i < len; i++) {
		res = res || isWarmUpFor(i);
	}

	return res;
}

function isWarmUpFor(i) {
	if (warmUpCol[i] != -1) {
		return data[i][counter[i]][warmUpCol[i]];
	} else {
		return false;
	}
}

while (countersUnderLimit()) {
	var d = [];
	for (var i = 0; i < len; i++) {
		d.push(i);
	}

	var tm = [];
	for (var i = 0; i < len; i++) {
		tm.push(getTm(counter[i], data[i]));
	}

	var stillWarmUp = isWarmUp();
	var min = null, minPos = null;
	for (var i = 0; i < len; i++) {
		if (!stillWarmUp || isWarmUpFor(i)) {
			if (min == null || (tm[i] != null && tm[i] < min)) {
				min = tm[i];
				minPos = i;
			}
		}
	}

	if (minPos != null) {
		d[0] = data[minPos][counter[minPos]][0];
		for (var i = 0; i < len; i++) {
			if ((!stillWarmUp || isWarmUpFor(i)) && tm[i] == min) {
				d[i + 1] = data[i][counter[i]][columns[i]];
				counter[i]++;
			} else {
				d[i + 1] = null;
			}
		}

		data_array_24.push(d);
	}
}
