create table user (
    id bigint generated by default as identity (start with 1) primary key,
    name varchar(225),
    email varchar(225),
    password varchar(255),
    role varchar(45),
    expired boolean default false not null,
    createdOn datetime,
    modifiedOn datetime,
    createdBy varchar(45),
    modifiedBy varchar(45)
);

create table make (
   id bigint generated by default as identity (start with 1) primary key,
   makeName varchar(45) not null,
   description varchar(200),
   createdOn datetime not null,
   modifiedOn datetime,
   createdBy varchar(45) not null,
   modifiedBy varchar(45)
 );

create table model (
    id bigint generated by default as identity (start with 1) primary key,
    modelName varchar(145) not null,
    makeId bigint not null,
    createdOn datetime not null,
    modifiedOn datetime,
    createdBy varchar(45) not null,
    modifiedBy varchar(45)
);

alter table model add foreign key (makeId) REFERENCES make (id);

create table customer (
    id bigint generated by default as identity (start with 1) primary key,
    name varchar(150) not null,
    address varchar(200),
    phone varchar(20),
    mobile varchar(20),
    email varchar(20),
    createdOn datetime not null,
    modifiedOn datetime,
    createdBy varchar(45) not null,
    modifiedBy varchar(45)
);

create table customer_additional_details (
    id bigint generated by default as identity (start with 1) primary key,
    customerId bigint not null,
    contactPerson varchar(150) not null,
    contactPhone varchar(20) not null,
    note varchar(500) not null,
    createdOn datetime not null,
    modifiedOn datetime,
    createdBy varchar(45) not null,
    modifiedBy varchar(45)
);

create table transaction (
    id bigint generated by default as identity (start with 1) primary key,
    tagNo varchar(45),
    dateReported datetime not null,
    customerId bigint not null,
    productCategory varchar(45) not null,
    makeId bigint not null,
    modelId bigint not null,
    serialNo varchar(45) not null,
    accessories varchar(200),
    complaintReported varchar(200),
    complaintDiagnosed varchar(200),
    engineerRemarks varchar(200),
    repairAction varchar(200),
    note varchar(500),
    status varchar(45) not null,
    createdOn datetime not null,
    modifiedOn datetime,
    createdBy varchar(45) not null,
    modifiedBy varchar(45)
);

create table companyterms (
    id bigint generated by default as identity (start with 1) primary key,
    companyName varchar(45),
    companyAddress varchar(500),
    companyPhone varchar(45),
    companyEmail varchar(45),
    companyWebsite varchar(45),
    terms varchar(5000),
    vatTin varchar(45),
    cstTin varchar(45),
    createdOn datetime not null,
    modifiedOn datetime,
    createdBy varchar(45) not null,
    modifiedBy varchar(45)
);

create table invoice (
    id bigint generated by default as identity (start with 1) primary key,
    transactionId bigint not null,
    description varchar(150) not null,
    serialNo varchar(150) not null,
    amount bigint not null,
    quantity integer not null,
    rate bigint not null,
    customerId bigint not null,
    customerName varchar(45) not null,
    tagNo varchar(45),
    createdOn datetime not null,
    modifiedOn datetime,
    createdBy varchar(45) not null,
    modifiedBy varchar(45)
);

