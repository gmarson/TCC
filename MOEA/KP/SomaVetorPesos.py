def soma():
	vetor = [484, 27, 860, 758, 659, 780, 139, 890, 300, 992, 984, 378, 700, 510, 485, 831, 835, 307, 757, 817, 710, 746, 579, 70, 913, 729, 116, 240, 227, 570, 202, 739, 305, 204, 999, 550, 128, 816, 602, 518, 175, 978, 179, 567, 997, 245, 432, 471, 150, 294]
	total = 0
	for valor in vetor:
		total += valor


	print("Total :"+str(total))
	print("Valor da soma de pesos: "+str(total/2.0))

soma()