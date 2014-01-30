#!/usr/bin/python
import sys

def main(filepath):
	seqnum = 0L
	with open(filepath, "r") as f:
		tempFile = None
		for line in f:
			if line.startswith('>'): # Cabecera de proteina
				if tempFile is not None: tempFile.close()
				tempFile = open(str(seqnum)+".fasta","w")
				seqnum += 1
			tempFile.write(line)
		tempFile.close()
	
	with open("data.properties", "w") as fproperties:
		fproperties.write(str(seqnum))
		
		
if __name__ == "__main__":
	main(sys.argv[1])