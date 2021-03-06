USE [master]
GO
/****** Object:  Database [Session2]    Script Date: 8/1/2021 9:42:33 PM ******/
CREATE DATABASE [Session2]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Session2', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL13.ADMINISTRATOR_SQL\MSSQL\DATA\Session2.mdf' , SIZE = 73728KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Session2_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL13.ADMINISTRATOR_SQL\MSSQL\DATA\Session2_log.ldf' , SIZE = 139264KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [Session2] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Session2].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Session2] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Session2] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Session2] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Session2] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Session2] SET ARITHABORT OFF 
GO
ALTER DATABASE [Session2] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Session2] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Session2] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Session2] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Session2] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Session2] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Session2] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Session2] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Session2] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Session2] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Session2] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Session2] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Session2] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Session2] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Session2] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Session2] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Session2] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Session2] SET RECOVERY FULL 
GO
ALTER DATABASE [Session2] SET  MULTI_USER 
GO
ALTER DATABASE [Session2] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Session2] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Session2] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Session2] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Session2] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'Session2', N'ON'
GO
ALTER DATABASE [Session2] SET QUERY_STORE = OFF
GO
USE [Session2]
GO
ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO
ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO
USE [Session2]
GO
/****** Object:  Table [dbo].[AssetGroups]    Script Date: 8/1/2021 9:42:33 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[AssetGroups](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_AssetTypes] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[AssetPhotos]    Script Date: 8/1/2021 9:42:33 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[AssetPhotos](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[AssetID] [bigint] NOT NULL,
	[AssetPhoto] [varchar](max) NULL,
 CONSTRAINT [PK__AssetPho__3214EC272D7D83A7] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Assets]    Script Date: 8/1/2021 9:42:33 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Assets](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[AssetSN] [nvarchar](20) NOT NULL,
	[AssetName] [nvarchar](150) NOT NULL,
	[DepartmentLocationID] [bigint] NOT NULL,
	[EmployeeID] [bigint] NOT NULL,
	[AssetGroupID] [bigint] NOT NULL,
	[Description] [nvarchar](2000) NOT NULL,
	[WarrantyDate] [date] NULL,
 CONSTRAINT [PK_Assets] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DepartmentLocations]    Script Date: 8/1/2021 9:42:33 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DepartmentLocations](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[DepartmentID] [bigint] NOT NULL,
	[LocationID] [bigint] NOT NULL,
	[StartDate] [date] NOT NULL,
	[EndDate] [date] NULL,
 CONSTRAINT [PK_DepartmentLocations] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Departments]    Script Date: 8/1/2021 9:42:33 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Departments](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](100) NOT NULL,
 CONSTRAINT [PK_Departments] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Employees]    Script Date: 8/1/2021 9:42:33 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employees](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[FirstName] [nvarchar](50) NOT NULL,
	[LastName] [nvarchar](50) NOT NULL,
	[Phone] [nvarchar](50) NOT NULL,
	[isAdmin] [bit] NULL,
	[Username] [nvarchar](50) NULL,
	[Password] [nvarchar](50) NULL,
 CONSTRAINT [PK_Employees] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Locations]    Script Date: 8/1/2021 9:42:33 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Locations](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](100) NOT NULL,
 CONSTRAINT [PK_Locations] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LongMaintenances]    Script Date: 8/1/2021 9:42:33 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LongMaintenances](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[AssetID] [bigint] NOT NULL,
	[TaskID] [int] NOT NULL,
	[ScheduleModel] [varchar](30) NOT NULL,
	[LMStartDate] [date] NOT NULL,
	[LMEndDate] [date] NOT NULL,
	[isComplete] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TaskMaintenances]    Script Date: 8/1/2021 9:42:33 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TaskMaintenances](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](200) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[AssetGroups] ON 

INSERT [dbo].[AssetGroups] ([ID], [Name]) VALUES (1, N'Hydraulic')
INSERT [dbo].[AssetGroups] ([ID], [Name]) VALUES (2, N'Pneumatic')
INSERT [dbo].[AssetGroups] ([ID], [Name]) VALUES (3, N'Electrical')
INSERT [dbo].[AssetGroups] ([ID], [Name]) VALUES (4, N'Mechanical ')
INSERT [dbo].[AssetGroups] ([ID], [Name]) VALUES (5, N'Fixed/Stationary')
INSERT [dbo].[AssetGroups] ([ID], [Name]) VALUES (6, N'Facility')
INSERT [dbo].[AssetGroups] ([ID], [Name]) VALUES (7, N'Buildings')
SET IDENTITY_INSERT [dbo].[AssetGroups] OFF
GO
SET IDENTITY_INSERT [dbo].[AssetPhotos] ON 


SET IDENTITY_INSERT [dbo].[AssetPhotos] OFF
GO
SET IDENTITY_INSERT [dbo].[Assets] ON 

INSERT [dbo].[Assets] ([ID], [AssetSN], [AssetName], [DepartmentLocationID], [EmployeeID], [AssetGroupID], [Description], [WarrantyDate]) VALUES (2, N'04/03/0001', N'Suction Line 852', 7, 8, 3, N'  ', CAST(N'2020-03-02' AS Date))
INSERT [dbo].[Assets] ([ID], [AssetSN], [AssetName], [DepartmentLocationID], [EmployeeID], [AssetGroupID], [Description], [WarrantyDate]) VALUES (3, N'01/01/0001', N'ZENY 3,5CFM Single-Stage 5 Pa Rotary Vane', 12, 1, 1, N'', CAST(N'2018-01-17' AS Date))
INSERT [dbo].[Assets] ([ID], [AssetSN], [AssetName], [DepartmentLocationID], [EmployeeID], [AssetGroupID], [Description], [WarrantyDate]) VALUES (4, N'05/04/0002', N'Volvo FH16', 5, 8, 4, N'', NULL)
INSERT [dbo].[Assets] ([ID], [AssetSN], [AssetName], [DepartmentLocationID], [EmployeeID], [AssetGroupID], [Description], [WarrantyDate]) VALUES (10014, N'03/06/0001', N'Test Asset', 9, 5, 6, N'   ', CAST(N'2013-07-19' AS Date))
SET IDENTITY_INSERT [dbo].[Assets] OFF
GO
SET IDENTITY_INSERT [dbo].[DepartmentLocations] ON 

INSERT [dbo].[DepartmentLocations] ([ID], [DepartmentID], [LocationID], [StartDate], [EndDate]) VALUES (1, 6, 3, CAST(N'2010-12-28' AS Date), CAST(N'2011-01-20' AS Date))
INSERT [dbo].[DepartmentLocations] ([ID], [DepartmentID], [LocationID], [StartDate], [EndDate]) VALUES (2, 6, 2, CAST(N'2015-12-27' AS Date), CAST(N'2019-08-20' AS Date))
INSERT [dbo].[DepartmentLocations] ([ID], [DepartmentID], [LocationID], [StartDate], [EndDate]) VALUES (3, 5, 2, CAST(N'1996-04-29' AS Date), NULL)
INSERT [dbo].[DepartmentLocations] ([ID], [DepartmentID], [LocationID], [StartDate], [EndDate]) VALUES (4, 5, 1, CAST(N'2002-03-04' AS Date), NULL)
INSERT [dbo].[DepartmentLocations] ([ID], [DepartmentID], [LocationID], [StartDate], [EndDate]) VALUES (5, 5, 1, CAST(N'1991-03-25' AS Date), CAST(N'2001-10-30' AS Date))
INSERT [dbo].[DepartmentLocations] ([ID], [DepartmentID], [LocationID], [StartDate], [EndDate]) VALUES (6, 4, 3, CAST(N'2012-05-28' AS Date), NULL)
INSERT [dbo].[DepartmentLocations] ([ID], [DepartmentID], [LocationID], [StartDate], [EndDate]) VALUES (7, 4, 2, CAST(N'2005-05-04' AS Date), NULL)
INSERT [dbo].[DepartmentLocations] ([ID], [DepartmentID], [LocationID], [StartDate], [EndDate]) VALUES (8, 3, 2, CAST(N'1992-10-17' AS Date), NULL)
INSERT [dbo].[DepartmentLocations] ([ID], [DepartmentID], [LocationID], [StartDate], [EndDate]) VALUES (9, 3, 3, CAST(N'2000-01-08' AS Date), NULL)
INSERT [dbo].[DepartmentLocations] ([ID], [DepartmentID], [LocationID], [StartDate], [EndDate]) VALUES (10, 2, 1, CAST(N'1993-12-25' AS Date), NULL)
INSERT [dbo].[DepartmentLocations] ([ID], [DepartmentID], [LocationID], [StartDate], [EndDate]) VALUES (11, 1, 2, CAST(N'2005-11-11' AS Date), NULL)
INSERT [dbo].[DepartmentLocations] ([ID], [DepartmentID], [LocationID], [StartDate], [EndDate]) VALUES (12, 1, 2, CAST(N'1991-01-17' AS Date), CAST(N'2000-02-02' AS Date))
SET IDENTITY_INSERT [dbo].[DepartmentLocations] OFF
GO
SET IDENTITY_INSERT [dbo].[Departments] ON 

INSERT [dbo].[Departments] ([ID], [Name]) VALUES (1, N'Exploration')
INSERT [dbo].[Departments] ([ID], [Name]) VALUES (2, N'Production')
INSERT [dbo].[Departments] ([ID], [Name]) VALUES (3, N'Transportation')
INSERT [dbo].[Departments] ([ID], [Name]) VALUES (4, N'R&D')
INSERT [dbo].[Departments] ([ID], [Name]) VALUES (5, N'Distribution')
INSERT [dbo].[Departments] ([ID], [Name]) VALUES (6, N'QHSE')
SET IDENTITY_INSERT [dbo].[Departments] OFF
GO
SET IDENTITY_INSERT [dbo].[Employees] ON 

INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (1, N'Martina', N'Winegarden', N'69232044381', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (2, N'Rashida', N'Brammer', N'70687629632', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (3, N'Mohamed', N'Krall', N'52688435003', NULL, N'mohamed', N'1234')
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (4, N'Shante', N'Karr', N'73706803851', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (5, N'Rosaura', N'Rames', N'70477806324', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (6, N'Toi', N'Courchesne', N'37756763508', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (7, N'Precious', N'Wismer', N'15287468908', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (8, N'Josefa', N'Otte', N'68886927765', NULL, N'josefa', N'1324')
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (9, N'Afton', N'Harrington', N'41731972558', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (10, N'Daphne', N'Morrow', N'49099375842', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (11, N'Dottie', N'Polson', N'91205317719', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (12, N'Alleen', N'Nally', N'26312971918', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (13, N'Hilton', N'Odom', N'66197770749', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (14, N'Shawn', N'Hillebrand', N'64091780262', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (15, N'Lorelei', N'Kettler', N'73606665126', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (16, N'Jalisa', N'Gossage', N'10484197749', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (17, N'Germaine', N'Sim', N'62454794026', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (18, N'Suzanna', N'Wollman', N'97932678482', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (19, N'Jennette', N'Besse', N'26229712670', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (20, N'Margherita', N'Anstine', N'87423893204', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (21, N'Earleen', N'Lambright', N'64658700776', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (22, N'Lyn', N'Valdivia', N'32010885662', 1, N'lyn', N'1234')
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (23, N'Alycia', N'Couey', N'41716866650', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (24, N'Lewis', N'Rousey', N'16716397946', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (25, N'Tanja', N'Profitt', N'77230010211', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (26, N'Cherie', N'Whyte', N'33510813739', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (27, N'Efren', N'Leaf', N'72090665294', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (28, N'Delta', N'Darcangelo', N'73136120450', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (29, N'Jess', N'Bodnar', N'12207277240', NULL, NULL, NULL)
INSERT [dbo].[Employees] ([ID], [FirstName], [LastName], [Phone], [isAdmin], [Username], [Password]) VALUES (30, N'Sudie', N'Parkhurst', N'26842289705', NULL, NULL, NULL)
SET IDENTITY_INSERT [dbo].[Employees] OFF
GO
SET IDENTITY_INSERT [dbo].[Locations] ON 

INSERT [dbo].[Locations] ([ID], [Name]) VALUES (1, N'Kazan')
INSERT [dbo].[Locations] ([ID], [Name]) VALUES (2, N'Volka')
INSERT [dbo].[Locations] ([ID], [Name]) VALUES (3, N'Moscow')
SET IDENTITY_INSERT [dbo].[Locations] OFF
GO
SET IDENTITY_INSERT [dbo].[TaskMaintenances] ON 

INSERT [dbo].[TaskMaintenances] ([ID], [Name]) VALUES (1, N'Engine Oil')
INSERT [dbo].[TaskMaintenances] ([ID], [Name]) VALUES (2, N'Engine Air Filter')
INSERT [dbo].[TaskMaintenances] ([ID], [Name]) VALUES (3, N'WindShield Wipers')
INSERT [dbo].[TaskMaintenances] ([ID], [Name]) VALUES (4, N'Tires')
INSERT [dbo].[TaskMaintenances] ([ID], [Name]) VALUES (5, N'Coolant Levels')
INSERT [dbo].[TaskMaintenances] ([ID], [Name]) VALUES (6, N'Spark Plugs')
INSERT [dbo].[TaskMaintenances] ([ID], [Name]) VALUES (7, N'Car Battery')
SET IDENTITY_INSERT [dbo].[TaskMaintenances] OFF
GO
ALTER TABLE [dbo].[LongMaintenances] ADD  DEFAULT ((0)) FOR [isComplete]
GO
ALTER TABLE [dbo].[AssetPhotos]  WITH CHECK ADD  CONSTRAINT [FK_AssetPhotos_Assets] FOREIGN KEY([AssetID])
REFERENCES [dbo].[Assets] ([ID])
GO
ALTER TABLE [dbo].[AssetPhotos] CHECK CONSTRAINT [FK_AssetPhotos_Assets]
GO
ALTER TABLE [dbo].[Assets]  WITH CHECK ADD  CONSTRAINT [FK_Assets_AssetGroups] FOREIGN KEY([AssetGroupID])
REFERENCES [dbo].[AssetGroups] ([ID])
GO
ALTER TABLE [dbo].[Assets] CHECK CONSTRAINT [FK_Assets_AssetGroups]
GO
ALTER TABLE [dbo].[Assets]  WITH CHECK ADD  CONSTRAINT [FK_Assets_DepartmentLocations] FOREIGN KEY([DepartmentLocationID])
REFERENCES [dbo].[DepartmentLocations] ([ID])
GO
ALTER TABLE [dbo].[Assets] CHECK CONSTRAINT [FK_Assets_DepartmentLocations]
GO
ALTER TABLE [dbo].[Assets]  WITH CHECK ADD  CONSTRAINT [FK_Assets_Employees] FOREIGN KEY([EmployeeID])
REFERENCES [dbo].[Employees] ([ID])
GO
ALTER TABLE [dbo].[Assets] CHECK CONSTRAINT [FK_Assets_Employees]
GO
ALTER TABLE [dbo].[DepartmentLocations]  WITH CHECK ADD  CONSTRAINT [FK_DepartmentLocations_Departments] FOREIGN KEY([DepartmentID])
REFERENCES [dbo].[Departments] ([ID])
GO
ALTER TABLE [dbo].[DepartmentLocations] CHECK CONSTRAINT [FK_DepartmentLocations_Departments]
GO
ALTER TABLE [dbo].[DepartmentLocations]  WITH CHECK ADD  CONSTRAINT [FK_DepartmentLocations_Locations] FOREIGN KEY([LocationID])
REFERENCES [dbo].[Locations] ([ID])
GO
ALTER TABLE [dbo].[DepartmentLocations] CHECK CONSTRAINT [FK_DepartmentLocations_Locations]
GO
ALTER TABLE [dbo].[LongMaintenances]  WITH CHECK ADD  CONSTRAINT [FK_LongMaintenances_Assets] FOREIGN KEY([AssetID])
REFERENCES [dbo].[Assets] ([ID])
GO
ALTER TABLE [dbo].[LongMaintenances] CHECK CONSTRAINT [FK_LongMaintenances_Assets]
GO
ALTER TABLE [dbo].[LongMaintenances]  WITH CHECK ADD  CONSTRAINT [FK_LongMaintenances_TaskMaintenances] FOREIGN KEY([TaskID])
REFERENCES [dbo].[TaskMaintenances] ([ID])
GO
ALTER TABLE [dbo].[LongMaintenances] CHECK CONSTRAINT [FK_LongMaintenances_TaskMaintenances]
GO
USE [master]
GO
ALTER DATABASE [Session2] SET  READ_WRITE 
GO
