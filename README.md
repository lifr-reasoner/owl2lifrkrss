# OWL 2 LiFR-syntax KRSS translator
**OWL2LiFRKRSS** is a translator service to convert [OWL](https://www.w3.org/OWL/) ontologies to the [LiFR reasoner](https://github.com/lifr-reasoner/lifr)'s variant of the [KRSS](https://franz.com/agraph/racer/krss-spec.pdf) syntax. 

------------

## Description ##
LiFR's syntax follows a lisp-like variant of the [KRSS](http://dl.kr.org/dl97/krss.ps) ontological notation.

The variant can be found in the following publication: 

Tsatsou, D., Dasiopoulou, S., Kompatsiaris, I., & Mezaris, V. (2014, May). LiFR: a lightweight fuzzy DL reasoner. In European Semantic Web Conference (pp. 263-267). Springer International Publishing. 

The translator uses the [OWL API](https://github.com/owlcs/owlapi). Local version available in /lib, however most recent version of the OWL API *should* work. 

### LiFR Semantics and Syntax ###
LiFR initially supported fuzzy DLP (f-DLP) semantics (as seen in the aforementioned publication). f-DLP is the fuzzy extension of [DLP](http://www.cs.man.ac.uk/~horrocks/Publications/download/2003/p117-grosof.pdf), a tractable knowledge representation fragment, closely related to the [OWL 2 RL](http://www.w3.org/TR/owl2-profiles/#OWL_2_RL) proﬁle, that combines classical DLs with Logic Programs (LP), thus combining ontologies with rules.

Since then, LiFR is extended beyond Horn clauses, since it supports definite clauses with complex heads. In addition, it is extended to support complex negation, therefore placing it within the ![](https://wikimedia.org/api/rest_v1/media/math/render/svg/2302a18e269dbecc43c57c0c2aced3bfae15278d)![](https://wikimedia.org/api/rest_v1/media/math/render/svg/19ef4c7b923a5125ac91aa491838a95ee15b804f)![](https://wikimedia.org/api/rest_v1/media/math/render/svg/0e9730a0ada0426927ff64141eb9f505eca132d4)![](https://wikimedia.org/api/rest_v1/media/math/render/svg/4e63ea009de5efbca2fc285b8550daaed577c6b8) <sup>-</sup> fragment, which is a sub-language of  ![](https://wikimedia.org/api/rest_v1/media/math/render/svg/2302a18e269dbecc43c57c0c2aced3bfae15278d)![](https://wikimedia.org/api/rest_v1/media/math/render/svg/19ef4c7b923a5125ac91aa491838a95ee15b804f)![](https://wikimedia.org/api/rest_v1/media/math/render/svg/0e9730a0ada0426927ff64141eb9f505eca132d4)![](https://wikimedia.org/api/rest_v1/media/math/render/svg/4e63ea009de5efbca2fc285b8550daaed577c6b8), with limited universal restrictions.

The fuzzy extension of ![](https://wikimedia.org/api/rest_v1/media/math/render/svg/2302a18e269dbecc43c57c0c2aced3bfae15278d)![](https://wikimedia.org/api/rest_v1/media/math/render/svg/19ef4c7b923a5125ac91aa491838a95ee15b804f)![](https://wikimedia.org/api/rest_v1/media/math/render/svg/0e9730a0ada0426927ff64141eb9f505eca132d4)![](https://wikimedia.org/api/rest_v1/media/math/render/svg/4e63ea009de5efbca2fc285b8550daaed577c6b8) <sup>-</sup> (*f*-![](https://wikimedia.org/api/rest_v1/media/math/render/svg/2302a18e269dbecc43c57c0c2aced3bfae15278d)![](https://wikimedia.org/api/rest_v1/media/math/render/svg/19ef4c7b923a5125ac91aa491838a95ee15b804f)![](https://wikimedia.org/api/rest_v1/media/math/render/svg/0e9730a0ada0426927ff64141eb9f505eca132d4)![](https://wikimedia.org/api/rest_v1/media/math/render/svg/4e63ea009de5efbca2fc285b8550daaed577c6b8) <sup>-</sup>) lies in the support of fuzzy assertions, restricted to concepts only, with an added support for weighted concept modifiers, while role assertions are currently treated as crisp with an imposed membership degree of ≥ 1.0 . The crisp operations intersection, union and implication, are extended to fuzzy sets and performed by t-norm, t-conorm and implication functions respectively. The fuzzy set operations of LiFR follow the operators of [Zadeh fuzzy logic](http://www-bisc.cs.berkeley.edu/Zadeh-1965.pdf). 

## Includes ##
- Concept Implication *(SubClassOf)* axioms
- Concept Equivalence *(EquivalentClasses)* axioms
- Concept Negation *(ObjectComplementOf)* axioms
- Existential Quantification *(ObjectSomeValuesFrom)* axioms 
- Universal Quantification *(ObjectAllValuesFrom)* axioms 
- Concept Disjointness *(DisjointClasses)*
- General Concept Inclusion Axioms
- Role *(Object Property)* axioms: 
	- Implication *(SubObjectPropertyOf)*
	- Inverse *(InverseObjectProperties)*
	- Domain *(ObjectPropertyDomain)*
	- Range *(ObjectPropertyRange)*
	- Transitive *(TransitiveObjectProperty)*
	- Symmetric *(SymmetricObjectProperty)*

**NOTE**: although supported in the translator, LiFR will reject Existential Quantification and Universal Quantification axioms that are outside of DLP, i.e.:
- *C \sqsubseteq \exists R.D* or *C SubClassOf (R ObjectSomeValuesFrom D)* 
- *(R ObjectAllValuesFrom D) SubClassOf C*)

## TODO ##
- Add Concept (Class) assertions
- Add Role (Object Property) assertions
- Add Weight modifiers