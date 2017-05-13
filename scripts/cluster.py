import numpy as np
from scipy.cluster.hierarchy import dendrogram, linkage
import matplotlib.pyplot as plt
import sys
import fileinput

inputfile = sys.argv[1]

matrix = []

matrix = np.loadtxt(inputfile, skiprows = 1)

mat = np.array(matrix)

dist_mat = mat
linkage_matrix = linkage(dist_mat, "ward")

plt.clf()

ddata = dendrogram(linkage_matrix)

label_colors = {'a': 'r', 'b': 'g', 'c': 'b', 'd': 'm'}

plt.show()
