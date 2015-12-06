Remarks
=======
* Supplied formulas are not homogeneous in term of mathematical operations signs. One formula uses a cross "x" and another one uses a point ".". It brings useless ambiguity to the test.
```
	(∑ Traded Price x Quantity) / ∑ Quantity
	(Fixed Dividend . Par Value) / Price
```
* P/E ration formula seems ambiguous because of Dividend denominator. What does it means? Last dividend, fixed dividend, dividend yield or something else? I took first option, but I'm not sure that it's correct.

* I'm using BigDecimal type for all currency manipulation. I'm not sure that it can be an optimal approach in the case of geometric mean, in most basic cases it can be overkill. It can be possible to proceed by logarithm, find the arithmetic mean and then exponentiate. I don't have enough of details to define which approach is most appropriate for this case.

Instructions
============

**Compile and/or test**
```
mvn compile
mvn test
```

**Run SSSM application**

```
mvn compile
mvn exec:java
```

**Explore shell instructions**

```
SSSM> ?l
```

**Available instructions**

abbrev  | name                          | params
------- | ------------------------------| -------
d       | dividend                      | (p1)
p       | peratio                       | (p1)
vwsp    | volume-weighted-stock-price   | ()
cg      | calculate-GBCE                | ()
s       | stock                         | (p1)
t       | trades                        | ()
b       | buy                           | (p1, p2, p3)
bn      | buy-now                       | (p1, p2)
sn      | sell-now                      | (p1, p2)
s       | sell                          | (p1, p2, p3)

**Dividend calculation**
```
SSSM> stock POP
SSSM> dividend 2.2
```
**P/E ratio calculation**
```
SSSM> stock POP
SSSM> peratio 45
```
**Record a trade**
```
SSSM> stock POP
SSSM> sell-now 34 5.05
```
```
SSSM> stock ALE
SSSM> sell 2015-12-06T17:37:34.756Z 34 5.05
```
```
SSSM> stock JOE
SSSM> buy 2015-12-06T17:37:34.756Z 100 455.67
```
```
SSSM> stock GIN
SSSM> buy-now 1000 .67
```
**Visualize trades**
```
SSSM> trades
```
**Calculate volume weighted stock price**
```
SSSM> stock TEA
SSSM> volume-weighted-stock-price
```
**Calculate the GBCE all share index**
```
SSSM> calculate-GBCE
```
