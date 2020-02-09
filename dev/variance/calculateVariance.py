import numpy as np
import matplotlib.pyplot as plt

values_mV = np.loadtxt("ljpValues.txt")
values_uV = values_mV * 1000
valuesMean_uV = np.mean(values_uV)
valuesMean_mV = valuesMean_uV / 1000.0
baselineValues_uV = values_uV - valuesMean_uV
n, bins, patches = plt.hist(baselineValues_uV, 100)
details = "n: %d, mean: %f mV, stdev: %f µV" % (
    len(values_uV), valuesMean_mV, np.std(values_uV))
plt.title("Repeated LJP Calculations")
plt.ylabel("Count")
plt.xlabel(f"Difference from Mean (µV)\n{details}")
plt.tight_layout()
plt.savefig("ljpVariance.png")
plt.show()
