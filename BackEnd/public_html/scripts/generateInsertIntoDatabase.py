#!/usr/bin/python
import sys

# I dunno wtf
def main(filepath):
	seqnum = 0L
	header = ""
	sequence = ""
	insertStmnt = "INSERT INTO proteins VALUES ({0},\"{1}\",\"{2}\");"

	with open(filepath, "r") as f:
		with open("insertProteins.txt", "w") as scriptFile:
			for line in f:
				if line.startswith('>'): # Cabecera de proteina
					if header != "": 
						scriptFile.write(insertStmnt.format(str(seqnum),header,sequence))
						scriptFile.write("\n")
					header = line.replace("\n","")
					seqnum += 1
					print seqnum
				else:
					sequence += line.replace("\n","")
			scriptFile.write(insertStmnt.format(str(seqnum),header,sequence))
			
if __name__ == "__main__":
	print "Beginning..."
	main(sys.argv[1])
	print "...Done!"