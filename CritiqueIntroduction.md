latex input:		mmd-memoir-header
Title:			Specification Critique
Author:			Group 03
Email:			se2020.grp03@cse.unsw.edu.au
Date:			05 August 2012
Base Header Level:	2
LaTeX Mode:		memoir  
latex input:		mmd-memoir-begin-doc
latex footer:		mmd-memoir-footer

#Specification Critique

##Introduction
###Invariants & Theorems
Invariants should express all constraints on the machine that are important to the integrity of the system. They are not merely used as a method to declare variable types. The invariants should be used to specify the semantic relationships between variables. It is also said that invariants should be as strong as necessary but no stronger. Theorems provide a way for checks to confirm those properties that are *"obviously"* true. It 
 
###Correctness & Consistency
Correctness & consistency is one of the important aspects in a model. Not only the model has to be correctly modelled, i.e. all proof obligations discharged, it has to be consistent with the requirements. The model also has to be complete, fulfilling all the requirements.

###Concrete
The models should describe behaviour, not details of how that behaviour is obtained. Therefore, models should be abstract, rather than concrete.

###Machine Sequence
The way the refinements are carried out in the model should be in a way that the previous machine has a link to the next. The refinements should carry over properties is related to the previous machine.

###Clarity
The model should be readable by humans. A model that looks complicated and impressive might not be a good model. Don't confuse complexity with quality. Readability of naming convention is also very important.

##Invariants & Theorems

###Invariant used to reinforce semantic relationships

###Group 4Semantic relationship are reasonably obvious in group 4's model- there were strong constraints in some machines that show linkage between each variables. These constraints were primarily relationship between size and numbers, and were particularly stronger in areas such as inventory and warehouse stock management. Such example would include usedSize and maxSize from the storage area machine.However in machines where integrity constraints were lackluster, it can be seen that this is due to the model’s several ‘small refinements’, where only one to four variables were only introduced. This became apparent with the newer variables having no linkage with the former variables, such as warehouseThreshold and a storage’s usedSize. Furthermore, basic number values and size comparisons were neglected, for example the relationship between the currentDay, a product’s timeToExpire and its expiryDate, despite including many of these in the earlier refinements. These relationships were only reinforced in the guards in the relating events.In one respect, the lack of constraints can be seen as acceptable, owing to the fact that this spec attempts to model real world restrictions through its idea of having general attributes, and essentially having one variable representing the many viable ‘loss’ constraints. ###Group 7Group 7’s invariants and their properties are similar to group 4’s, in that some constraints were included and appropriate at times, and lacking in the later refinements- many of the later invariants were type based constraints. It was also noted that this group’s spec had the most number of refinements out of the four.Similar to group 4, each of these refinements, especially the latter ones introduced one to three variables. There was also a distinct lack of important associations between older and newer variables, for example, the stock threshold in each floor and the floor’s capacity as well as orders and their amount not exceeding the capacity. This can be largely due to high numbers of refinements, where the separated related variable become easily lost between each refinement.The constraints were largely relevant to warehousestock, and floor locations, similar to group 4’s, where it establishes separate compartments and entities a product has to exist in. For example, the linkage between products, stock, floor, backroom and warehouse in the location machine. Compartmentalisation is also seen with registers, in particular moneybox, storeSafe and sumOfRegisters, in the later refinements. ###Group 11Group 11 had both appropriate and unnecessary invariants. Given its high number of undischarged POs, it can be seen that many of these unnecessary invariants backfired and rooted logical and correctness issues. Similar both group 4 and 7, many of these integrity constraints were not sustained throughout the refinements, with lesser in the loyalty, discounts and schedule machines. Integrity constraints were stronger in areas such as transactions, where linkage was very clear between how the instore cashtill operates with its topay and transactionInProgress variables, accompanied by comments that describes this relation. The spec also at times associates variables from its previous refinements, such as products in the shoppingcart with the number of stocks available to purchase.There were no invariants in the Users machine where authorisation and privileges were enforced. Many of these relationships depended on its event based guards, with no invariant that ties together what a higher level access level can general ‘modify’. The lack of constraints in the more complicated concepts such as the spec’s scheduling system created unclear and ambiguous functionalities in the spec. For example, whether if a schedule can be both a specialSchedule and orderSchedule at the same time and function concurrently. Moreover, this ambiguity in the model underscores flaws in the original requirements and design, rather than just the spec/event b model itself.###Group 3Based on the above three specs, we (group 3) derived a number comparable improvements to be made. The main issue with our spec concerns the lack of constraints that describes the semantic relationship between variables, and this can be seen through the number of proof obligations our model had in total. Through identifying and understanding other models, we were able to highlight how weak constraints impact the cohesion as a system, and the corrective value of the model.It was also observed that while the above three specs operates with multiple functioning trolleys of products by different users simultaneously, our model handles one transaction at a time, given one moneybox and trolley, and this can somewhat infer a distinct relationship with the number of complex constraints our spec had to consider.Despite having no integrity based constraints, some of our guards were sufficiently comprehensive, and thus can be translated and generalised to become more appropriate invariants. For example, the interaction between the purchases of ReservedStock, ReservedNum, stock and trolley with ActiveProd can be readily prepared and translated to an invariant based on the BuyReserve and ReserveProduct events. With these guards, our team can consider outlining possible and more effective invariants to add and improve in the later stages.

###Use of theorems
In all four groups, no theorems were included, and will be disregarded for this basis of this critique.

##Correctness & Consistency
###group 4

###group 7

###group 11

###group 3

##Concrete
###group 4

###group 7

###group 11

###group 3

##Machine Sequence
###group 4

###group 7

###group 11

###group 3

##Clarity
###group 4

###group 7

###group 11

###group 3

#"New" Specification Summary

#Project Plan

#Appendix