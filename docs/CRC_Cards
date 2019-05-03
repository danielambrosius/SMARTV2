CRC Cards
=========

## ModelView

|Responsibilities               |Collaborators                |
|-------------------------------|-----------------------------|
|Provides main interface        |App					      |
|Designate save- loadPath       |FileChooser				  |

## Model

|Responsibilities               |Collaborators                |
|-------------------------------|-----------------------------|
|Hold equations			        |Equation				      |
|Reconstruct solvable formulas  |							  |

## SaverLoader

|Responsibilities               |Collaborators                |
|-------------------------------|-----------------------------|
|Serialize and store instances  |App					      |
|Load and deserialize instances |App					      |

## Experiment

|Responsibilities               |Collaborators                |
|-------------------------------|-----------------------------|
|Hold the time settings		    |App					      |
|Hold the values for parameters |App				  		  |
|Hold state initial values		|App						  |
|Run the experiment				|SolverThread				  |

## App

|Responsibilities               |Collaborators                |
|-------------------------------|-----------------------------|
|Handle input from ModelView    |ModelView, Model             |
|Handle input from ExperimentView|ExperimentView, Experiment  |
|Handle saving/loading requests |SaverLoader, ModelView       |
|Create Experiment instance     |Experiment, ModelVieuw       |
|Hold Experiment instance       |Experiment                   |
|Create Model instance          |Model, ModelView             |