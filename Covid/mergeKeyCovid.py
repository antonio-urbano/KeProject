import csv
with open('covid_sources.csv', errors='ignore') as f:
    reader = csv.reader(f)
    with open('covid_uniqueKey.csv', 'a', newline='') as g:
        writer = csv.writer(g)
        for row in reader:
            new_row = [' '.join([row[0], row[1]])] + row[1:]
            writer.writerow(new_row)