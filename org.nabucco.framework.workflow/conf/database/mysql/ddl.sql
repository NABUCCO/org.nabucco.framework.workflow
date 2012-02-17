DROP TABLE IF EXISTS work_instantiable_effect;

CREATE TABLE work_instantiable_effect (
	class_name LONGTEXT NOT NULL,
	id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_property_condition;

CREATE TABLE work_property_condition (
	expression VARCHAR(255) NOT NULL,
	id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_role_condition;

CREATE TABLE work_role_condition (
	role_name VARCHAR(255) NOT NULL,
	id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_instantiable_condition;

CREATE TABLE work_instantiable_condition (
	class_name LONGTEXT NOT NULL,
	id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_script_effect;

CREATE TABLE work_script_effect (
	script_name VARCHAR(255) NOT NULL,
	id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_group_condition;

CREATE TABLE work_group_condition (
	group_name VARCHAR(255) NOT NULL,
	id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_modification_effect;

CREATE TABLE work_modification_effect (
	path VARCHAR(255) NOT NULL,
	value VARCHAR(255) NOT NULL,
	id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_log_effect;

CREATE TABLE work_log_effect (
	message VARCHAR(255) NOT NULL,
	id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_sub_workflow_effect;

CREATE TABLE work_sub_workflow_effect (
	definition_name VARCHAR(255) NOT NULL,
	assigned_user VARCHAR(255),
	assigned_group VARCHAR(255),
	assigned_role VARCHAR(255),
	summary VARCHAR(255) NOT NULL,
	id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_boolean_condition;

CREATE TABLE work_boolean_condition (
	operator VARCHAR(255),
	id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_signal_trigger;

CREATE TABLE work_signal_trigger (
	signal_type VARCHAR(255),
	id BIGINT NOT NULL,
	signal_id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_assignee_condition;

CREATE TABLE work_assignee_condition (
	id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_workflow_effect;

CREATE TABLE work_workflow_effect (
	id BIGINT NOT NULL AUTO_INCREMENT,
	version BIGINT,
	description VARCHAR(255) NOT NULL,
	effect_type VARCHAR(255),
	name VARCHAR(255) NOT NULL,
	owner VARCHAR(12) NOT NULL,
	workflow_transition_effect_list_id BIGINT,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_dynamic_constraint_effect;

CREATE TABLE work_dynamic_constraint_effect (
	editable BIT,
	max_length INT,
	max_multiplicity INT,
	min_length INT,
	min_multiplicity INT,
	property_name VARCHAR(255),
	visible BIT,
	id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_permission_condition;

CREATE TABLE work_permission_condition (
	permission_name VARCHAR(255) NOT NULL,
	id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_assignee_effect;

CREATE TABLE work_assignee_effect (
	new_group VARCHAR(255),
	new_role VARCHAR(255),
	new_user VARCHAR(255),
	id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_context_effect;

CREATE TABLE work_context_effect (
	id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_time_trigger;

CREATE TABLE work_time_trigger (
	timer VARCHAR(255) NOT NULL,
	id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_workflow_condition;

CREATE TABLE work_workflow_condition (
	id BIGINT NOT NULL AUTO_INCREMENT,
	version BIGINT,
	condition_type VARCHAR(255),
	description VARCHAR(255) NOT NULL,
	name VARCHAR(255) NOT NULL,
	owner VARCHAR(12) NOT NULL,
	workflow_condition_composite_condition_list_id BIGINT,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_workflow_context;

CREATE TABLE work_workflow_context (
	id BIGINT NOT NULL AUTO_INCREMENT,
	version BIGINT,
	property_name VARCHAR(255),
	xml LONGTEXT,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_workflow_definition;

CREATE TABLE work_workflow_definition (
	id BIGINT NOT NULL AUTO_INCREMENT,
	version BIGINT,
	description VARCHAR(255) NOT NULL,
	name VARCHAR(255) NOT NULL,
	owner VARCHAR(12) NOT NULL,
	workflow_type VARCHAR(255),
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_workflow_instance;

CREATE TABLE work_workflow_instance (
	id BIGINT NOT NULL AUTO_INCREMENT,
	version BIGINT,
	assigned_group VARCHAR(255),
	assigned_role VARCHAR(255),
	assigned_user VARCHAR(255),
	creation_time DATETIME NOT NULL,
	creator VARCHAR(255) NOT NULL,
	description LONGTEXT,
	due_date DATE,
	name VARCHAR(255) NOT NULL,
	owner VARCHAR(12) NOT NULL,
	summary VARCHAR(255) NOT NULL,
	type VARCHAR(255),
	functional_type_ref_id BIGINT NOT NULL,
	priority_ref_id BIGINT,
	context_id BIGINT,
	current_entry_id BIGINT NOT NULL,
	definition_id BIGINT NOT NULL,
	workflow_instance_sub_instances_id BIGINT,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_workflow_instance_component_relation;

CREATE TABLE work_workflow_instance_component_relation (
	id BIGINT NOT NULL AUTO_INCREMENT,
	version BIGINT,
	relation_type VARCHAR(255) NOT NULL,
	source_id BIGINT NOT NULL,
	functional_type VARCHAR(255) NOT NULL,
	target_id BIGINT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_workflow_instance_entry;

CREATE TABLE work_workflow_instance_entry (
	id BIGINT NOT NULL AUTO_INCREMENT,
	version BIGINT,
	comment LONGTEXT,
	modification_time DATETIME NOT NULL,
	modifier VARCHAR(255) NOT NULL,
	owner VARCHAR(12) NOT NULL,
	context_id BIGINT,
	state_id BIGINT NOT NULL,
	workflow_instance_entry_list_id BIGINT,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_workflow_signal;

CREATE TABLE work_workflow_signal (
	id BIGINT NOT NULL AUTO_INCREMENT,
	version BIGINT,
	description VARCHAR(255) NOT NULL,
	name VARCHAR(255) NOT NULL,
	owner VARCHAR(12) NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_workflow_signal_container;

CREATE TABLE work_workflow_signal_container (
	id BIGINT NOT NULL AUTO_INCREMENT,
	version BIGINT,
	signal_name VARCHAR(255) NOT NULL,
	signal_id BIGINT NOT NULL,
	workflow_definition_signal_list_id BIGINT,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_workflow_state;

CREATE TABLE work_workflow_state (
	id BIGINT NOT NULL AUTO_INCREMENT,
	version BIGINT,
	description VARCHAR(255) NOT NULL,
	name VARCHAR(255) NOT NULL,
	owner VARCHAR(12) NOT NULL,
	type VARCHAR(255),
	workflow_definition_state_list_id BIGINT,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_workflow_state_constraint;

CREATE TABLE work_workflow_state_constraint (
	id BIGINT NOT NULL AUTO_INCREMENT,
	version BIGINT,
	editable BIT NOT NULL,
	property_path VARCHAR(255),
	visible BIT NOT NULL,
	workflow_state_constraint_list_id BIGINT,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_workflow_transition;

CREATE TABLE work_workflow_transition (
	id BIGINT NOT NULL AUTO_INCREMENT,
	version BIGINT,
	description VARCHAR(255) NOT NULL,
	name VARCHAR(255) NOT NULL,
	owner VARCHAR(12) NOT NULL,
	comment_cardinality VARCHAR(255) NOT NULL,
	condition_id BIGINT,
	source_id BIGINT NOT NULL,
	target_id BIGINT NOT NULL,
	trigger_id BIGINT NOT NULL,
	workflow_definition_transition_list_id BIGINT,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS work_workflow_trigger;

CREATE TABLE work_workflow_trigger (
	id BIGINT NOT NULL AUTO_INCREMENT,
	version BIGINT,
	description VARCHAR(255) NOT NULL,
	name VARCHAR(255) NOT NULL,
	owner VARCHAR(12) NOT NULL,
	type VARCHAR(255),
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE INDEX FK5074A367395345EE ON work_workflow_instance (definition_id ASC);

CREATE INDEX FK5074A36728152299 ON work_workflow_instance (current_entry_id ASC);

CREATE INDEX FK5074A367D514F449 ON work_workflow_instance (context_id ASC);

CREATE INDEX FKB1AFA59A44572E0C ON work_workflow_instance (workflow_instance_sub_instances_id ASC);

CREATE INDEX IDX_WORKFLOWINSTANCE01 ON work_workflow_instance (creator ASC, assigned_user ASC);

CREATE INDEX IDX_WORKFLOWINSTANCE02 ON work_workflow_instance (creator ASC, assigned_group ASC, assigned_role ASC);

CREATE INDEX IDX_WORKFLOWINSTANCE03 ON work_workflow_instance (creator ASC, assigned_role ASC);

CREATE INDEX IDX_WORKFLOWINSTANCE04 ON work_workflow_instance (assigned_user ASC);

CREATE INDEX IDX_WORKFLOWINSTANCE05 ON work_workflow_instance (assigned_group ASC, assigned_role ASC);

CREATE INDEX IDX_WORKFLOWINSTANCE06 ON work_workflow_instance (assigned_role ASC);

CREATE INDEX FKB1AFA59AFF064686 ON work_workflow_instance_entry (state_id ASC);

CREATE INDEX FKB1AFA59AD514F449 ON work_workflow_instance_entry (context_id ASC);

CREATE INDEX FKB1AFA59A44572E0C ON work_workflow_instance_entry (workflow_instance_entry_list_id ASC);

CREATE INDEX IDX_WORKFLOWINSTANCEENTRY01 ON work_workflow_instance_entry (modifier ASC);

CREATE INDEX FKC0805ED69983C8B0 ON work_workflow_instance_component_relation (target_id ASC);

CREATE INDEX FK69510FA77FD77986 ON work_workflow_transition (target_id ASC);

CREATE INDEX FK69510FA74B387EBC ON work_workflow_transition (source_id ASC);

CREATE INDEX FK69510FA79E9313C5 ON work_workflow_transition (workflow_definition_transition_list_id ASC);

CREATE INDEX FK69510FA72208BEF0 ON work_workflow_transition (trigger_id ASC);

CREATE INDEX FK69510FA710D2D873 ON work_workflow_transition (condition_id ASC);

CREATE INDEX FK9F8976FF74EA7A9 ON work_workflow_state (workflow_definition_state_list_id ASC);

CREATE INDEX FK28B0C60FD0C32B09 ON work_signal_trigger (id ASC);

CREATE INDEX FK28B0C60F9991E18A ON work_signal_trigger (signal_id ASC);

CREATE INDEX FK993198B4D0C32B09 ON work_time_trigger (id ASC);

CREATE INDEX FK890D3889568DC117 ON work_workflow_condition (workflow_condition_composite_condition_list_id ASC);

CREATE INDEX FKEE51A995881BACF ON work_assignee_condition (id ASC);

CREATE INDEX FK6E1230165881BACF ON work_boolean_condition (id ASC);

CREATE INDEX FK1137662D5881BACF ON work_group_condition (id ASC);

CREATE INDEX FKF5298F195881BACF ON work_permission_condition (id ASC);

CREATE INDEX FK3D171B1F5881BACF ON work_property_condition (id ASC);

CREATE INDEX FKD227CF805881BACF ON work_role_condition (id ASC);

CREATE INDEX FK38FE54E316BEF5C1 ON work_workflow_effect (workflow_transition_effect_list_id ASC);

CREATE INDEX FK4BB02CD3D15DFD73 ON work_assignee_effect (id ASC);

CREATE INDEX FK31B5110FD15DFD73 ON work_context_effect (id ASC);

CREATE INDEX FKD9C51285D15DFD73 ON work_dynamic_constraint_effect (id ASC);

CREATE INDEX FKD47A9A40D15DFD73 ON work_instantiable_effect (id ASC);

CREATE INDEX FKDA77C8DAD15DFD73 ON work_log_effect (id ASC);

CREATE INDEX FKC6E43F66D15DFD73 ON work_modification_effect (id ASC);

CREATE INDEX FKE84E2D7D15DFD73 ON work_script_effect (id ASC);

CREATE INDEX FK252EA07CA401E818 ON work_workflow_signal_container (workflow_definition_signal_list_id ASC);

CREATE INDEX FK252EA07C9991E18A ON work_workflow_signal_container (signal_id ASC);

CREATE INDEX FK4289EF1D6E1361E5 ON work_workflow_state_constraint (workflow_state_constraint_list_id ASC);


ALTER TABLE work_assignee_condition ADD CONSTRAINT FKEE51A995881BACF FOREIGN KEY (id)
	REFERENCES work_workflow_condition (id);

