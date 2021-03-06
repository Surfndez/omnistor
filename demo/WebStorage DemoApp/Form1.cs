using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;
using System.Net;
using System.IO;
using System.Text;
using System.Xml;
using System.Threading;
using System.Timers;
using Microsoft.Win32;
using Ecareme.Utility;
using System.Collections.Specialized;
using System.Diagnostics;
using System.Security.Cryptography;
using System.Web;



namespace DemoApp
{
    /// <summary>
    /// Summary description for Form1.	
    /// /// </summary>
    public class Form1 : System.Windows.Forms.Form
    {
        private System.ComponentModel.IContainer components;
        string version = "0.2.2.10";
        string versionString;
        
        string serviceGatewayURL = "";
        string servicePortalURL = "https://asuscloudportal01.asuswebstorage.com";//"https://sp.yostore.net";
        string rootID = "-5";    
        string infoRelayURL = "";
        string webRelayURL = "";
        string jobRelayURL = "";
        string userID = "";
        string userPassword = "";
        string userLanguage = "zh_TW";
         string tokenString = "";
        string etagString = "";
        string packageDisplay = "";
        DateTime unix_start_time = new DateTime(1970, 1, 1);

        string appcodeID = "";

        long lastActionTime = 0;
        long lastActionDelta = 0;
        long lastActionLength = 0;
        string lastActionCommand = "";

        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.FolderBrowserDialog folderBrowserDialog1;

        //string approxyURL = "https://approxy.asuswebstorage.com/api/";

        private int BUFFER_SIZE = 1024 * 1024;

        string sequenceID = "123456";
        public string commandProgKey = "";
        string stringProgKey = "ABBCW9RR2D8GG5AFFTTE504C3A7ADEC";
        private Label versionLabel;

        private TabControl tabControl1;
        private TabPage tabPage2;
        private TreeView folderTreeView;
        private ContextMenuStrip contextMenuStrip1;
        private ToolStripMenuItem AddModifyMetadataToolStripMenuItem;
        private TabPage tabPage4;
        private CheckBox checkBox2;
        private Label label19;
        private Label label20;
        private TextBox userPasswordTB;
        private TextBox userIDTB;
        private Button browseBtn;
        private CheckBox showIDCB;
        private ToolStripMenuItem UploadfileToolStripMenuItem;
        private Label label1;
        private TextBox appCodeTB;
        private ToolStripMenuItem createAFolderToolStripMenuItem;
        private Label label2;
        private TextBox sidTB;
        private ToolStripMenuItem transcodeToFLVToolStripMenuItem;
        private Panel panelTranscode;
        private Button button1;
        private TextBox argTB;
        private TextBox extTB;
        private Button button2;
        private Button button3;
        private TextBox jobUUIDTB;
        private Label label7;
        private Label label5;
        private Label label4;
        private Label label3;
        private TextBox sgURLTB;
        private Label label6;
        private TextBox tokenTB;
        private Button loginBtn;
        private Label label10;
        private TextBox jrURLTB;
        private Label label9;
        private TextBox wrURLTB;
        private Label label8;
        private TextBox irURLTB;
        private Label label11;
        private TextBox expireDateTB;
        private Label label14;
        private Label label15;
        private TextBox usedSpaceTB;
        private Label label13;
        private Label label12;
        private TextBox packageSizeTB;
        private TextBox txtProgKey;
        private Label label16;
        private Button btnBinUpload;
        private ImageList imageList1;

        class EntryInfo
        {
            public int entryType = 0; // 0 is folder, 1 is file
            public string entryID = "";
            public string display = "";
            public string status = "";
            public int folderCount = 0;
            public int fileCount = 0;
            public string parentID = "";

        }


        public Form1()
        {
            //
            // Required for Windows Form Designer support
            //
            InitializeComponent();

            //
            // TODO: Add any constructor code after InitializeComponent call
            //
        }

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                if (components != null)
                {
                    components.Dispose();
                }
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code
        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.folderBrowserDialog1 = new System.Windows.Forms.FolderBrowserDialog();
            this.versionLabel = new System.Windows.Forms.Label();
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.tabPage4 = new System.Windows.Forms.TabPage();
            this.txtProgKey = new System.Windows.Forms.TextBox();
            this.label16 = new System.Windows.Forms.Label();
            this.label14 = new System.Windows.Forms.Label();
            this.label15 = new System.Windows.Forms.Label();
            this.usedSpaceTB = new System.Windows.Forms.TextBox();
            this.label13 = new System.Windows.Forms.Label();
            this.label12 = new System.Windows.Forms.Label();
            this.packageSizeTB = new System.Windows.Forms.TextBox();
            this.label11 = new System.Windows.Forms.Label();
            this.expireDateTB = new System.Windows.Forms.TextBox();
            this.label10 = new System.Windows.Forms.Label();
            this.jrURLTB = new System.Windows.Forms.TextBox();
            this.label9 = new System.Windows.Forms.Label();
            this.wrURLTB = new System.Windows.Forms.TextBox();
            this.label8 = new System.Windows.Forms.Label();
            this.irURLTB = new System.Windows.Forms.TextBox();
            this.loginBtn = new System.Windows.Forms.Button();
            this.label3 = new System.Windows.Forms.Label();
            this.sgURLTB = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.tokenTB = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.sidTB = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.appCodeTB = new System.Windows.Forms.TextBox();
            this.checkBox2 = new System.Windows.Forms.CheckBox();
            this.label19 = new System.Windows.Forms.Label();
            this.label20 = new System.Windows.Forms.Label();
            this.userPasswordTB = new System.Windows.Forms.TextBox();
            this.userIDTB = new System.Windows.Forms.TextBox();
            this.tabPage2 = new System.Windows.Forms.TabPage();
            this.panelTranscode = new System.Windows.Forms.Panel();
            this.label7 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.jobUUIDTB = new System.Windows.Forms.TextBox();
            this.button3 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.button1 = new System.Windows.Forms.Button();
            this.argTB = new System.Windows.Forms.TextBox();
            this.extTB = new System.Windows.Forms.TextBox();
            this.showIDCB = new System.Windows.Forms.CheckBox();
            this.browseBtn = new System.Windows.Forms.Button();
            this.folderTreeView = new System.Windows.Forms.TreeView();
            this.contextMenuStrip1 = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.AddModifyMetadataToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.UploadfileToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.createAFolderToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.transcodeToFLVToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.btnBinUpload = new System.Windows.Forms.Button();
            this.tabControl1.SuspendLayout();
            this.tabPage4.SuspendLayout();
            this.tabPage2.SuspendLayout();
            this.panelTranscode.SuspendLayout();
            this.contextMenuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(753, 398);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(216, 22);
            this.textBox1.TabIndex = 15;
            this.textBox1.Text = "textBox1";
            this.textBox1.Visible = false;
            // 
            // versionLabel
            // 
            this.versionLabel.AutoSize = true;
            this.versionLabel.Location = new System.Drawing.Point(560, 384);
            this.versionLabel.Name = "versionLabel";
            this.versionLabel.Size = new System.Drawing.Size(0, 12);
            this.versionLabel.TabIndex = 19;
            this.versionLabel.Visible = false;
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tabPage4);
            this.tabControl1.Controls.Add(this.tabPage2);
            this.tabControl1.Location = new System.Drawing.Point(18, 12);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(456, 449);
            this.tabControl1.TabIndex = 27;
            // 
            // tabPage4
            // 
            this.tabPage4.Controls.Add(this.txtProgKey);
            this.tabPage4.Controls.Add(this.label16);
            this.tabPage4.Controls.Add(this.label14);
            this.tabPage4.Controls.Add(this.label15);
            this.tabPage4.Controls.Add(this.usedSpaceTB);
            this.tabPage4.Controls.Add(this.label13);
            this.tabPage4.Controls.Add(this.label12);
            this.tabPage4.Controls.Add(this.packageSizeTB);
            this.tabPage4.Controls.Add(this.label11);
            this.tabPage4.Controls.Add(this.expireDateTB);
            this.tabPage4.Controls.Add(this.label10);
            this.tabPage4.Controls.Add(this.jrURLTB);
            this.tabPage4.Controls.Add(this.label9);
            this.tabPage4.Controls.Add(this.wrURLTB);
            this.tabPage4.Controls.Add(this.label8);
            this.tabPage4.Controls.Add(this.irURLTB);
            this.tabPage4.Controls.Add(this.loginBtn);
            this.tabPage4.Controls.Add(this.label3);
            this.tabPage4.Controls.Add(this.sgURLTB);
            this.tabPage4.Controls.Add(this.label6);
            this.tabPage4.Controls.Add(this.tokenTB);
            this.tabPage4.Controls.Add(this.label2);
            this.tabPage4.Controls.Add(this.sidTB);
            this.tabPage4.Controls.Add(this.label1);
            this.tabPage4.Controls.Add(this.appCodeTB);
            this.tabPage4.Controls.Add(this.checkBox2);
            this.tabPage4.Controls.Add(this.label19);
            this.tabPage4.Controls.Add(this.label20);
            this.tabPage4.Controls.Add(this.userPasswordTB);
            this.tabPage4.Controls.Add(this.userIDTB);
            this.tabPage4.Location = new System.Drawing.Point(4, 21);
            this.tabPage4.Name = "tabPage4";
            this.tabPage4.Size = new System.Drawing.Size(448, 424);
            this.tabPage4.TabIndex = 3;
            this.tabPage4.Text = "Login";
            this.tabPage4.UseVisualStyleBackColor = true;
            // 
            // txtProgKey
            // 
            this.txtProgKey.Location = new System.Drawing.Point(131, 130);
            this.txtProgKey.Name = "txtProgKey";
            this.txtProgKey.Size = new System.Drawing.Size(277, 22);
            this.txtProgKey.TabIndex = 59;
            // 
            // label16
            // 
            this.label16.AutoSize = true;
            this.label16.Location = new System.Drawing.Point(40, 130);
            this.label16.Name = "label16";
            this.label16.Size = new System.Drawing.Size(46, 12);
            this.label16.TabIndex = 58;
            this.label16.Text = "ProgKey";
            // 
            // label14
            // 
            this.label14.AutoSize = true;
            this.label14.Location = new System.Drawing.Point(245, 242);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(23, 12);
            this.label14.TabIndex = 57;
            this.label14.Text = "MB";
            // 
            // label15
            // 
            this.label15.AutoSize = true;
            this.label15.Location = new System.Drawing.Point(40, 242);
            this.label15.Name = "label15";
            this.label15.Size = new System.Drawing.Size(58, 12);
            this.label15.TabIndex = 56;
            this.label15.Text = "Used Space";
            // 
            // usedSpaceTB
            // 
            this.usedSpaceTB.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.usedSpaceTB.Location = new System.Drawing.Point(131, 240);
            this.usedSpaceTB.Name = "usedSpaceTB";
            this.usedSpaceTB.Size = new System.Drawing.Size(108, 22);
            this.usedSpaceTB.TabIndex = 55;
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Location = new System.Drawing.Point(245, 214);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(23, 12);
            this.label13.TabIndex = 54;
            this.label13.Text = "MB";
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Location = new System.Drawing.Point(40, 214);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(65, 12);
            this.label12.TabIndex = 53;
            this.label12.Text = "Package Size";
            // 
            // packageSizeTB
            // 
            this.packageSizeTB.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.packageSizeTB.Location = new System.Drawing.Point(131, 212);
            this.packageSizeTB.Name = "packageSizeTB";
            this.packageSizeTB.Size = new System.Drawing.Size(108, 22);
            this.packageSizeTB.TabIndex = 52;
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Location = new System.Drawing.Point(40, 186);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(78, 12);
            this.label11.TabIndex = 51;
            this.label11.Text = "Expiration Date";
            // 
            // expireDateTB
            // 
            this.expireDateTB.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.expireDateTB.Location = new System.Drawing.Point(131, 184);
            this.expireDateTB.Name = "expireDateTB";
            this.expireDateTB.Size = new System.Drawing.Size(181, 22);
            this.expireDateTB.TabIndex = 50;
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Location = new System.Drawing.Point(40, 385);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(17, 12);
            this.label10.TabIndex = 49;
            this.label10.Text = "JR";
            // 
            // jrURLTB
            // 
            this.jrURLTB.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.jrURLTB.Location = new System.Drawing.Point(93, 383);
            this.jrURLTB.Name = "jrURLTB";
            this.jrURLTB.Size = new System.Drawing.Size(315, 22);
            this.jrURLTB.TabIndex = 48;
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(40, 357);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(24, 12);
            this.label9.TabIndex = 47;
            this.label9.Text = "WR";
            // 
            // wrURLTB
            // 
            this.wrURLTB.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.wrURLTB.Location = new System.Drawing.Point(93, 355);
            this.wrURLTB.Name = "wrURLTB";
            this.wrURLTB.Size = new System.Drawing.Size(315, 22);
            this.wrURLTB.TabIndex = 46;
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(40, 329);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(17, 12);
            this.label8.TabIndex = 45;
            this.label8.Text = "IR";
            // 
            // irURLTB
            // 
            this.irURLTB.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.irURLTB.Location = new System.Drawing.Point(93, 327);
            this.irURLTB.Name = "irURLTB";
            this.irURLTB.Size = new System.Drawing.Size(315, 22);
            this.irURLTB.TabIndex = 44;
            // 
            // loginBtn
            // 
            this.loginBtn.Location = new System.Drawing.Point(295, 12);
            this.loginBtn.Name = "loginBtn";
            this.loginBtn.Size = new System.Drawing.Size(113, 67);
            this.loginBtn.TabIndex = 43;
            this.loginBtn.Text = "Login";
            this.loginBtn.UseVisualStyleBackColor = true;
            this.loginBtn.Click += new System.EventHandler(this.button4_Click);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(40, 273);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(19, 12);
            this.label3.TabIndex = 42;
            this.label3.Text = "SG";
            // 
            // sgURLTB
            // 
            this.sgURLTB.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.sgURLTB.Location = new System.Drawing.Point(93, 271);
            this.sgURLTB.Name = "sgURLTB";
            this.sgURLTB.Size = new System.Drawing.Size(315, 22);
            this.sgURLTB.TabIndex = 41;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(40, 301);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(35, 12);
            this.label6.TabIndex = 40;
            this.label6.Text = "Token";
            // 
            // tokenTB
            // 
            this.tokenTB.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.tokenTB.Location = new System.Drawing.Point(93, 299);
            this.tokenTB.Name = "tokenTB";
            this.tokenTB.Size = new System.Drawing.Size(315, 22);
            this.tokenTB.TabIndex = 39;
            // 
            // label2
            // 
            this.label2.Location = new System.Drawing.Point(40, 96);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(61, 24);
            this.label2.TabIndex = 21;
            this.label2.Text = "SID";
            // 
            // sidTB
            // 
            this.sidTB.Location = new System.Drawing.Point(131, 96);
            this.sidTB.Name = "sidTB";
            this.sidTB.Size = new System.Drawing.Size(127, 22);
            this.sidTB.TabIndex = 20;
            this.sidTB.Text = "appdemo";
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(40, 68);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(61, 24);
            this.label1.TabIndex = 19;
            this.label1.Text = "APPNAME";
            this.label1.Visible = false;
            // 
            // appCodeTB
            // 
            this.appCodeTB.Location = new System.Drawing.Point(131, 68);
            this.appCodeTB.Name = "appCodeTB";
            this.appCodeTB.Size = new System.Drawing.Size(127, 22);
            this.appCodeTB.TabIndex = 18;
            this.appCodeTB.Text = "APPNAME";
            this.appCodeTB.Visible = false;
            // 
            // checkBox2
            // 
            this.checkBox2.AutoSize = true;
            this.checkBox2.Location = new System.Drawing.Point(42, 156);
            this.checkBox2.Name = "checkBox2";
            this.checkBox2.Size = new System.Drawing.Size(299, 16);
            this.checkBox2.TabIndex = 17;
            this.checkBox2.Text = "Generate Debug Info in Desktop (DemoApp_debuglog.txt)";
            this.checkBox2.UseVisualStyleBackColor = true;
            // 
            // label19
            // 
            this.label19.Location = new System.Drawing.Point(40, 12);
            this.label19.Name = "label19";
            this.label19.Size = new System.Drawing.Size(85, 24);
            this.label19.TabIndex = 14;
            this.label19.Text = "WebStorage ID";
            // 
            // label20
            // 
            this.label20.Location = new System.Drawing.Point(40, 39);
            this.label20.Name = "label20";
            this.label20.Size = new System.Drawing.Size(61, 24);
            this.label20.TabIndex = 15;
            this.label20.Text = "Password";
            // 
            // userPasswordTB
            // 
            this.userPasswordTB.Location = new System.Drawing.Point(131, 40);
            this.userPasswordTB.Name = "userPasswordTB";
            this.userPasswordTB.PasswordChar = 'x';
            this.userPasswordTB.Size = new System.Drawing.Size(127, 22);
            this.userPasswordTB.TabIndex = 13;
            // 
            // userIDTB
            // 
            this.userIDTB.Location = new System.Drawing.Point(131, 12);
            this.userIDTB.Name = "userIDTB";
            this.userIDTB.Size = new System.Drawing.Size(127, 22);
            this.userIDTB.TabIndex = 1;
            // 
            // tabPage2
            // 
            this.tabPage2.Controls.Add(this.btnBinUpload);
            this.tabPage2.Controls.Add(this.panelTranscode);
            this.tabPage2.Controls.Add(this.showIDCB);
            this.tabPage2.Controls.Add(this.browseBtn);
            this.tabPage2.Controls.Add(this.folderTreeView);
            this.tabPage2.Location = new System.Drawing.Point(4, 21);
            this.tabPage2.Name = "tabPage2";
            this.tabPage2.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage2.Size = new System.Drawing.Size(448, 424);
            this.tabPage2.TabIndex = 1;
            this.tabPage2.Text = "File System";
            this.tabPage2.UseVisualStyleBackColor = true;
            // 
            // panelTranscode
            // 
            this.panelTranscode.Controls.Add(this.label7);
            this.panelTranscode.Controls.Add(this.label5);
            this.panelTranscode.Controls.Add(this.label4);
            this.panelTranscode.Controls.Add(this.jobUUIDTB);
            this.panelTranscode.Controls.Add(this.button3);
            this.panelTranscode.Controls.Add(this.button2);
            this.panelTranscode.Controls.Add(this.button1);
            this.panelTranscode.Controls.Add(this.argTB);
            this.panelTranscode.Controls.Add(this.extTB);
            this.panelTranscode.Location = new System.Drawing.Point(81, 146);
            this.panelTranscode.Name = "panelTranscode";
            this.panelTranscode.Size = new System.Drawing.Size(290, 226);
            this.panelTranscode.TabIndex = 39;
            this.panelTranscode.Visible = false;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(72, 83);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(41, 12);
            this.label7.TabIndex = 8;
            this.label7.Text = "jobuuid";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(40, 52);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(20, 12);
            this.label5.TabIndex = 7;
            this.label5.Text = "arg";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(72, 24);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(53, 12);
            this.label4.TabIndex = 6;
            this.label4.Text = "output file";
            // 
            // jobUUIDTB
            // 
            this.jobUUIDTB.Location = new System.Drawing.Point(125, 80);
            this.jobUUIDTB.Name = "jobUUIDTB";
            this.jobUUIDTB.Size = new System.Drawing.Size(140, 22);
            this.jobUUIDTB.TabIndex = 5;
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(166, 122);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(68, 27);
            this.button3.TabIndex = 4;
            this.button3.Text = "Check";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(166, 170);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(72, 27);
            this.button2.TabIndex = 3;
            this.button2.Text = "Close";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(45, 120);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(72, 29);
            this.button1.TabIndex = 2;
            this.button1.Text = "Start";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // argTB
            // 
            this.argTB.Location = new System.Drawing.Point(78, 52);
            this.argTB.Name = "argTB";
            this.argTB.Size = new System.Drawing.Size(187, 22);
            this.argTB.TabIndex = 1;
            this.argTB.Text = "-ar 22050 -r 29.97 -f flv";
            // 
            // extTB
            // 
            this.extTB.Location = new System.Drawing.Point(162, 24);
            this.extTB.Name = "extTB";
            this.extTB.Size = new System.Drawing.Size(103, 22);
            this.extTB.TabIndex = 0;
            this.extTB.Text = "out.flv";
            // 
            // showIDCB
            // 
            this.showIDCB.AutoSize = true;
            this.showIDCB.Location = new System.Drawing.Point(123, 15);
            this.showIDCB.Name = "showIDCB";
            this.showIDCB.Size = new System.Drawing.Size(114, 16);
            this.showIDCB.TabIndex = 34;
            this.showIDCB.Text = "Show file/folder ID";
            this.showIDCB.UseVisualStyleBackColor = true;
            // 
            // browseBtn
            // 
            this.browseBtn.Location = new System.Drawing.Point(27, 15);
            this.browseBtn.Name = "browseBtn";
            this.browseBtn.Size = new System.Drawing.Size(79, 36);
            this.browseBtn.TabIndex = 27;
            this.browseBtn.Text = "Browse";
            this.browseBtn.UseVisualStyleBackColor = true;
            this.browseBtn.Click += new System.EventHandler(this.button14_Click_1);
            // 
            // folderTreeView
            // 
            this.folderTreeView.ContextMenuStrip = this.contextMenuStrip1;
            this.folderTreeView.Font = new System.Drawing.Font("新細明體", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(136)));
            this.folderTreeView.FullRowSelect = true;
            this.folderTreeView.ImageIndex = 0;
            this.folderTreeView.ImageList = this.imageList1;
            this.folderTreeView.Location = new System.Drawing.Point(27, 68);
            this.folderTreeView.Name = "folderTreeView";
            this.folderTreeView.SelectedImageIndex = 0;
            this.folderTreeView.Size = new System.Drawing.Size(394, 331);
            this.folderTreeView.TabIndex = 2;
            this.folderTreeView.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.folderTreeView_AfterSelect);
            this.folderTreeView.Click += new System.EventHandler(this.folderTreeView_Click);
            // 
            // contextMenuStrip1
            // 
            this.contextMenuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.AddModifyMetadataToolStripMenuItem,
            this.UploadfileToolStripMenuItem,
            this.createAFolderToolStripMenuItem,
            this.transcodeToFLVToolStripMenuItem});
            this.contextMenuStrip1.Name = "contextMenuStrip1";
            this.contextMenuStrip1.Size = new System.Drawing.Size(161, 92);
            this.contextMenuStrip1.Text = "確認內容";
            // 
            // AddModifyMetadataToolStripMenuItem
            // 
            this.AddModifyMetadataToolStripMenuItem.Name = "AddModifyMetadataToolStripMenuItem";
            this.AddModifyMetadataToolStripMenuItem.Size = new System.Drawing.Size(160, 22);
            this.AddModifyMetadataToolStripMenuItem.Text = "Browse/Download";
            this.AddModifyMetadataToolStripMenuItem.Click += new System.EventHandler(this.DeleteModifyMetadataToolStripMenuItem_Click);
            // 
            // UploadfileToolStripMenuItem
            // 
            this.UploadfileToolStripMenuItem.Name = "UploadfileToolStripMenuItem";
            this.UploadfileToolStripMenuItem.Size = new System.Drawing.Size(160, 22);
            this.UploadfileToolStripMenuItem.Text = "Upload a file";
            this.UploadfileToolStripMenuItem.Click += new System.EventHandler(this.UploadFileToolStripMenuItem_Click);
            // 
            // createAFolderToolStripMenuItem
            // 
            this.createAFolderToolStripMenuItem.Name = "createAFolderToolStripMenuItem";
            this.createAFolderToolStripMenuItem.Size = new System.Drawing.Size(160, 22);
            this.createAFolderToolStripMenuItem.Text = "Create a folder";
            this.createAFolderToolStripMenuItem.Click += new System.EventHandler(this.createAFolderToolStripMenuItem_Click);
            // 
            // transcodeToFLVToolStripMenuItem
            // 
            this.transcodeToFLVToolStripMenuItem.Name = "transcodeToFLVToolStripMenuItem";
            this.transcodeToFLVToolStripMenuItem.Size = new System.Drawing.Size(160, 22);
            this.transcodeToFLVToolStripMenuItem.Text = "FFMpeg Transcode";
            this.transcodeToFLVToolStripMenuItem.Click += new System.EventHandler(this.transcodeToFLVToolStripMenuItem_Click);
            // 
            // imageList1
            // 
            this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
            this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList1.Images.SetKeyName(0, "folder.ico");
            this.imageList1.Images.SetKeyName(1, "file.ico");
            this.imageList1.Images.SetKeyName(2, "logo.ico");
            // 
            // btnBinUpload
            // 
            this.btnBinUpload.Location = new System.Drawing.Point(286, 15);
            this.btnBinUpload.Name = "btnBinUpload";
            this.btnBinUpload.Size = new System.Drawing.Size(84, 35);
            this.btnBinUpload.TabIndex = 40;
            this.btnBinUpload.Text = "Binary Upload";
            this.btnBinUpload.UseVisualStyleBackColor = true;
            this.btnBinUpload.Click += new System.EventHandler(this.btnBinUpload_Click);
            // 
            // Form1
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(5, 15);
            this.ClientSize = new System.Drawing.Size(495, 473);
            this.Controls.Add(this.tabControl1);
            this.Controls.Add(this.versionLabel);
            this.Controls.Add(this.textBox1);
            this.Name = "Form1";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "ASUS WebStorage DemoApp";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.tabControl1.ResumeLayout(false);
            this.tabPage4.ResumeLayout(false);
            this.tabPage4.PerformLayout();
            this.tabPage2.ResumeLayout(false);
            this.tabPage2.PerformLayout();
            this.panelTranscode.ResumeLayout(false);
            this.panelTranscode.PerformLayout();
            this.contextMenuStrip1.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }
        #endregion

        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main(string[] args)
        {
            Form1 f1 = new Form1();
            Application.Run(f1);
        }

        public string makeAuthorizeString()
        {
            //Declare OAuth Header Parameters
            string signatureMethod = "HMAC-SHA1";
            string timestamp = Convert.ToString(Times.GetTimestamp(DateTime.Now));
            string nonce = timestamp;

            StringBuilder oAuthParams = new StringBuilder();
            oAuthParams.Append("nonce=").Append(nonce)
                       .Append("&signature_method=").Append(signatureMethod)
                       .Append("&timestamp=").Append(timestamp);
            string oAuthParasURLEn = Crypto.UrlEncodeUpperCase(oAuthParams.ToString());
            string signature = Crypto.doHMacSH1(oAuthParasURLEn, stringProgKey);

            StringBuilder oAuth = new StringBuilder();
            oAuth.Append("signature_method=\"").Append(signatureMethod).Append("\",")
                 .Append("timestamp=\"").Append(timestamp).Append("\",")
                 .Append("nonce=\"").Append(nonce).Append("\",")
                 .Append("signature=\"").Append(signature).Append("\"");

            return oAuth.ToString();
        }

        public string postData(string rUrl, FileStream fs, long fileSize)
        {
            string backStr;
            backStr = "";
            if (checkBox2.Checked == true)
            {
                Ecareme.Utility.log.writeLog("postData :" + rUrl);
            }

            WebRequest httpRequest;

            httpRequest = WebRequest.Create(rUrl);
            httpRequest.Method = "POST";

            httpRequest.Headers.Add("Authorization", makeAuthorizeString());
            httpRequest.Headers.Add("Cookie", "Cookie=OMNISTORE_VER=1_0;a=" + lastActionCommand + ";t=" + lastActionTime + ";d=" + lastActionDelta + ";l=" + lastActionLength + ";c=0;v=" + versionString + ";EEE_MANU=TEST;EEE_PROD=TEST;OS_VER=" + Convert.ToString(Environment.OSVersion.Version.Major) + " " + Convert.ToString(Environment.OSVersion.Version.Minor) + ";sid=" + sequenceID + ";");

            UTF8Encoding encoding = new UTF8Encoding();
            Stream newStream;
            byte[] data = new byte[BUFFER_SIZE];
            int readLen = 0;

            httpRequest.ContentLength = fileSize;

            newStream = httpRequest.GetRequestStream();

            while ( (readLen = fs.Read(data, 0, BUFFER_SIZE)) > 0 )
            {
                newStream.Write(data, 0, readLen);
            }

            //newStream.Write(data, 0, data.Length);
            newStream.Close();

            WebResponse httpResponse = httpRequest.GetResponse();


            StreamReader readStream;
            readStream = new StreamReader(httpResponse.GetResponseStream(), encoding);
            char[] read = new char[257];
            // Reads 256 characters at a time.
            int count = readStream.Read(read, 0, 256);
            while (count > 0)
            {
                // Dumps the 256 characters to a string and displays the string to the console.
                string str = new string(read, 0, count);
                backStr = backStr + str;
                count = readStream.Read(read, 0, 256);
                str = null;
            }
            httpResponse.Close();

            encoding = null;
            readStream = null;
            read = null;
            if (checkBox2.Checked == true)
                Ecareme.Utility.log.writeLog("postData return :" + backStr);
            return backStr;
        }

        public string postData(string rUrl, string dataStr)
        {

            string backStr;
            backStr = "";
            if (checkBox2.Checked == true)
            {
                if (rUrl.IndexOf("/file/trunk") > 0)
                    Ecareme.Utility.log.writeLog("postData :" + rUrl);
                else
                    Ecareme.Utility.log.writeLog("postData :" + rUrl + " " + dataStr);
            }

            WebRequest httpRequest;

            try
            {
                httpRequest = WebRequest.Create(rUrl);
                httpRequest.Method = "POST";

                /*
                if (dataStr.Length > 0)
                {
                    httpRequest.Method = "POST";
                }
                else
                {
                    httpRequest.Method = "GET";
                }
                 */

                httpRequest.Headers.Add("Authorization", makeAuthorizeString());


                httpRequest.Headers.Add("Cookie", "Cookie=OMNISTORE_VER=1_0;a=" + lastActionCommand + ";t=" + lastActionTime + ";d=" + lastActionDelta + ";l=" + lastActionLength + ";c=0;v=" + versionString + ";EEE_MANU=TEST;EEE_PROD=TEST;OS_VER=" + Convert.ToString(Environment.OSVersion.Version.Major) + " " + Convert.ToString(Environment.OSVersion.Version.Minor) + ";sid=" + sequenceID + ";");

                UTF8Encoding encoding = new UTF8Encoding();
                if (dataStr != null && dataStr.Trim().Length > 0)
                {
                    Stream newStream;
                    byte[] data;
                    data = encoding.GetBytes(dataStr);
                    httpRequest.ContentLength = data.Length;

                    newStream = httpRequest.GetRequestStream();
                    newStream.Write(data, 0, data.Length);
                    newStream.Close();
                }

                WebResponse httpResponse = httpRequest.GetResponse();

                StreamReader readStream;
                readStream = new StreamReader(httpResponse.GetResponseStream(), encoding);
                char[] read = new char[257];
                // Reads 256 characters at a time.
                int count = readStream.Read(read, 0, 256);
                while (count > 0)
                {
                    // Dumps the 256 characters to a string and displays the string to the console.
                    string str = new string(read, 0, count);
                    backStr = backStr + str;
                    count = readStream.Read(read, 0, 256);
                    str = null;
                }
                httpResponse.Close();

                encoding = null;
                readStream = null;
                read = null;
                if (checkBox2.Checked == true)
                    Ecareme.Utility.log.writeLog("postData return :" + backStr);
                return backStr;
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public string createFolder(string parentFolderID, string currentFolderName)
        {
            string mXML, responseXML = "";
            XmlDocument ydom = new XmlDocument();
            XmlNodeList xNodes;
            string currentFolderID;

            // create the folder
            string creationTime = Convert.ToString(Convert.ToUInt64((DateTime.Now - unix_start_time).TotalSeconds));

            string finfo = "";

            mXML = "<create><token>" + tokenString + "</token><scrip>" + etagString +
                   "</scrip><userid>" + userID + "</userid><parent>" + parentFolderID +
                   "</parent><display>" + SimpleBase64.Encode(currentFolderName) +
                   "</display><isencrypted>0</isencrypted><attribute><creationtime>" + creationTime +
                   "</creationtime><lastaccesstime>" + creationTime +
                   "</lastaccesstime><lastwritetime>" + creationTime +
                   "</lastwritetime><finfo>" + finfo +
                   "</finfo></attribute><issharing>0</issharing></create>";
            responseXML = postData(infoRelayURL + "/folder/create/", mXML);

            ydom.LoadXml(responseXML);
            xNodes = ydom.SelectNodes("create");
            etagString = xNodes.Item(0).SelectNodes("scrip").Item(0).InnerText;
            currentFolderID = xNodes.Item(0).SelectNodes("id").Item(0).InnerText;
            ydom = null;
            return currentFolderID;
        }

        private void Form1_Load(object sender, System.EventArgs e)
        {
            this.Text = this.Text + " " + version;
            folderTreeView.ImageList = this.imageList1;
        }


        private void requestServiceGateway()
        {
            string mXML = "";
            string responseXML = "";
            try
            {
                XmlDocument ydom = new XmlDocument();
                XmlNodeList xNodes;

                MD5 md5 = new MD5CryptoServiceProvider();
                string hashedPassword = BitConverter.ToString(md5.ComputeHash(Encoding.UTF8.GetBytes(userPassword.ToLower()))).Replace("-", "");
                md5 = null;

                mXML = "<requestservicegateway><userid>" + userID + "</userid><password>" + hashedPassword + "</password><language>" + userLanguage + "</language><service>1</service><time>2008/1/1</time></requestservicegateway>";
                responseXML = postData(servicePortalURL + "/member/requestservicegateway/", mXML);

                if (responseXML == "")
                {
                    serviceGatewayURL = "";
                    MessageBox.Show ("Please check Internet connectivity!");
                    return;

                }
                ydom.LoadXml(responseXML);
                try
                {
                    xNodes = ydom.SelectNodes("requestservicegateway");
                    serviceGatewayURL = "https://" + xNodes.Item(0).SelectNodes("servicegateway").Item(0).InnerText;
                }
                catch
                {
                    MessageBox.Show ("Please input valid ID and password!");
                }
                if (serviceGatewayURL == "")
                {
                    userID = "";
                    userPassword = "";
                    MessageBox.Show ("Please input valid ID and password!");
                    return;
                }
                ydom = null;

            }
            catch
            {
                serviceGatewayURL = "";
            }
        }

        private void acquireToken()
        {
            string mXML;
            string responseXML;

            XmlDocument ydom = new XmlDocument();
            XmlNodeList xNodes;
            XmlNodeList yNodes;

            try
            {

                if (serviceGatewayURL == "")
                {
                    requestServiceGateway();
                    sgURLTB.Text = serviceGatewayURL;
                    if (serviceGatewayURL == "") return;
                }

                MD5 md5 = new MD5CryptoServiceProvider();
                string hashedPassword = BitConverter.ToString(md5.ComputeHash(Encoding.UTF8.GetBytes(userPassword.ToLower()))).Replace("-", "");
                md5 = null;

                mXML = "<aaa><userid>" + userID + "</userid><password>" + hashedPassword + "</password><time>2008/1/1</time></aaa>";

                responseXML = postData(serviceGatewayURL + "/member/acquiretoken/", mXML);

                ydom.LoadXml(responseXML);

                xNodes = ydom.SelectNodes("aaa");

                tokenString = xNodes.Item(0).SelectNodes("token").Item(0).InnerText;

                infoRelayURL = "https://" + xNodes.Item(0).SelectNodes("inforelay").Item(0).InnerText;

                irURLTB.Text = infoRelayURL;

                webRelayURL = "https://" + xNodes.Item(0).SelectNodes("webrelay").Item(0).InnerText;

                wrURLTB.Text = webRelayURL;

                jobRelayURL = "https://" + xNodes.Item(0).SelectNodes("jobrelay").Item(0).InnerText;

                jrURLTB.Text = jobRelayURL;

                yNodes = xNodes.Item(0).SelectNodes("package");
                packageDisplay = yNodes.Item(0).SelectNodes("display").Item(0).InnerText;
                

                this.tokenTB.Text = tokenString;

                ydom = null;
            }
            catch
            {
                tokenString = "";
            }
        }

        private string getCurrentFolderID(string currentFolderName, string parentFolderID)
        {
            string mXML, responseXML;
            if (tokenString == "")
            {
                acquireToken();
            }
            if (etagString == "")
                etagString = "system.new";
            try
            {

                string currentFolderID;
                XmlDocument ydom = new XmlDocument();
                XmlNodeList xNodes;
                mXML = "<propfind><token>" + tokenString + "</token><scrip>" + etagString + "</scrip><userid>" + userID + "</userid><parent>" + parentFolderID + "</parent><find>" + SimpleBase64.Encode(currentFolderName) + "</find><type>system.folder</type></propfind>";
                responseXML = postData(infoRelayURL + "/find/propfind/", mXML);
                if (responseXML.IndexOf("<status>2</status>") != -1)
                {
                    tokenString = "";
                    etagString = "";
                    ydom = null;
                    return "";
                }
                ydom.LoadXml(responseXML);
                xNodes = ydom.SelectNodes("propfind");
                string returnType;

                etagString = xNodes.Item(0).SelectNodes("scrip").Item(0).InnerText;
                returnType = xNodes.Item(0).SelectNodes("type").Item(0).InnerText;
                currentFolderID = xNodes.Item(0).SelectNodes("id").Item(0).InnerText;
                return currentFolderID;
            }
            catch { return ""; }
        }

        public static String Exec(String cmd, String path, String parms, out int exit_code)
        {
            System.Diagnostics.ProcessStartInfo psi = new System.Diagnostics.ProcessStartInfo(path + cmd, parms);
            psi.RedirectStandardOutput = false;
            psi.UseShellExecute = false;
            psi.WindowStyle = ProcessWindowStyle.Normal;

            System.Diagnostics.Process p = System.Diagnostics.Process.Start(psi);
            //string tool_output = p.StandardOutput.ReadToEnd();
            //p.WaitForExit();
            exit_code = 0;
            string tool_output = "";
            return tool_output;
        }

        private void DeleteModifyMetadataToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                if (folderTreeView.SelectedNode != null)
                {
                    EntryInfo selectedEntry = (EntryInfo)folderTreeView.SelectedNode.Tag;
                    if (selectedEntry.entryType == 1)
                    {
                        Cursor.Current = Cursors.WaitCursor;

                        int outp;
                        Exec("\"c:\\program files\\internet explorer\\iexplore.exe\"", "", webRelayURL + "/webrelay/directdownload/" + tokenString + "/?fi=" + selectedEntry.entryID, out outp);
                        Cursor.Current = Cursors.Default;
                    }
                    else
                    {


                        Cursor.Current = Cursors.WaitCursor;
                        folderTreeView.SelectedNode.Nodes.Clear();

                        if (tokenString == "")
                        {
                            acquireToken();
                            if (tokenString == "") return;
                        }
                        if (etagString == "")
                            etagString = "system.new";

                        XmlDocument ydom = new XmlDocument();
                        XmlNodeList xNodes;
                        XmlNodeList fileNodes;
                        XmlNodeList folderNodes;
                        string mXML;
                        string responseXML;



                        mXML = "<browse><token>" + tokenString + "</token><scrip>" + etagString + "</scrip><userid>" + userID + "</userid><folderid>" + selectedEntry.entryID + "</folderid><language>" + userLanguage + "</language></browse>";
                        responseXML = postData(infoRelayURL + "/folder/browse/", mXML);
                        if (responseXML.IndexOf("<status>0</status>") == -1)
                        {
                            tokenString = "";
                            etagString = "";
                            return;
                        }
                        ydom.LoadXml(responseXML);

                        if (responseXML == "")
                        {
                            return;
                        }
                        xNodes = ydom.SelectNodes("browse");
                        etagString = xNodes.Item(0).SelectNodes("scrip").Item(0).InnerText;
                        fileNodes = xNodes.Item(0).SelectNodes("file");
                        folderNodes = xNodes.Item(0).SelectNodes("folder");


                        for (int j = 0; j <= folderNodes.Count - 1; j++)
                        {
                            string entryID = folderNodes.Item(j).SelectNodes("id").Item(0).InnerText;
                            string displayName = SimpleBase64.Decode(folderNodes.Item(j).SelectNodes("display").Item(0).InnerText);

                            EntryInfo tempEntry = new EntryInfo();
                            tempEntry.entryID = entryID;
                            tempEntry.entryType = 0;
                            tempEntry.display = displayName;
                            tempEntry.parentID = selectedEntry.entryID;

                            TreeNode tempNode;
                            if (showIDCB.Checked == true)
                                tempNode = new TreeNode(displayName + "|" + entryID);
                            else
                                tempNode = new TreeNode(displayName);
                            tempNode.ImageIndex = 0;
                            tempNode.Tag = tempEntry;
                            folderTreeView.SelectedNode.Nodes.Add(tempNode);
                        }

                        for (int j = 0; j <= fileNodes.Count - 1; j++)
                        {
                            string entryID = fileNodes.Item(j).SelectNodes("id").Item(0).InnerText;
                            string displayName = SimpleBase64.Decode(fileNodes.Item(j).SelectNodes("display").Item(0).InnerText);
                            string status;
                            try
                            {
                                status = fileNodes.Item(j).SelectNodes("status").Item(0).InnerText;
                            }
                            catch { status = ""; }

                            EntryInfo tempEntry = new EntryInfo();
                            tempEntry.entryID = entryID;
                            tempEntry.entryType = 1;
                            tempEntry.display = displayName;
                            tempEntry.status = status;
                            tempEntry.parentID = selectedEntry.entryID;

                            TreeNode tempNode;
                            if (showIDCB.Checked == true)
                                tempNode = new TreeNode(displayName + "|" + entryID);
                            else
                                tempNode = new TreeNode(displayName);
                            tempNode.ImageIndex = 1;
                            tempNode.Tag = tempEntry;
                            folderTreeView.SelectedNode.Nodes.Add(tempNode);
                        }
                        Cursor.Current = Cursors.Default;
                    }
                }
            }
            catch (Exception exception) { string a = exception.Message; }

        }
        
        private void button14_Click_1(object sender, EventArgs e)
        {
            if (tokenString == "")
            {
                MessageBox.Show("Please login first!");
                return;
            }

            appCodeTB.Text = "MySyncFolder";

            string parent = rootID;
            TreeNode subNode = null;
            bool isFist = false;

            if (folderTreeView.Nodes.Count > 0)
            {
                if (folderTreeView.SelectedNode == null)
                    return;
                else
                {
                    subNode = folderTreeView.SelectedNode;
                    EntryInfo ei = (EntryInfo)subNode.Tag;
                    if (ei.entryType == 1)
                        return;
                    else
                        parent = ei.entryID;
                }
            }


            Cursor.Current = Cursors.WaitCursor;

            if (appcodeID == "")
            {
                appcodeID = getCurrentFolderID(appCodeTB.Text, rootID);

                if (appcodeID == "")
                {
                    // new APPCODE Folder
                    appcodeID = createFolder(rootID, appCodeTB.Text);
                    if (appcodeID == "")
                    {
                        MessageBox.Show("Create Folder Error, please try later!");
                        return;
                    }
                }
                parent = appcodeID;
                isFist = true;
            }

            XmlDocument ydom = new XmlDocument();
            XmlNodeList xNodes;
            XmlNodeList fileNodes;
            XmlNodeList folderNodes;
            string mXML;
            string responseXML;

            mXML = "<browse><token>" + tokenString + "</token><scrip>" + etagString + "</scrip><userid>" + userID + "</userid><folderid>" + parent + "</folderid><language>" + userLanguage + "</language></browse>";
            responseXML = postData(infoRelayURL + "/folder/browse/", mXML);
            if (responseXML.IndexOf("<status>0</status>") == -1)
            {
                tokenString = "";
                etagString = "";
                return;
            }
            ydom.LoadXml(responseXML);

            if (responseXML == "")
            {
                return;
            }
            xNodes = ydom.SelectNodes("browse");
            fileNodes = xNodes.Item(0).SelectNodes("file");
            folderNodes = xNodes.Item(0).SelectNodes("folder");

            EntryInfo tempEntry = null;
            if (isFist)
            {
                folderTreeView.Nodes.Clear();
                subNode = new TreeNode(appCodeTB.Text);

                if (showIDCB.Checked == true)
                    subNode.Text = appCodeTB.Text + " | " + appcodeID;
                else
                    subNode.Text = appCodeTB.Text;

                tempEntry = new EntryInfo();
                tempEntry.entryID = appcodeID;
                tempEntry.entryType = 0;
                tempEntry.display = appCodeTB.Text;

                subNode.ImageIndex = 0;

                subNode.Tag = tempEntry;

                folderTreeView.Nodes.Add(subNode);
            }
            else
                subNode.Nodes.Clear();

            for (int j = 0; j <= folderNodes.Count - 1; j++)
            {
                string entryID = folderNodes.Item(j).SelectNodes("id").Item(0).InnerText;
                string displayName = SimpleBase64.Decode(folderNodes.Item(j).SelectNodes("display").Item(0).InnerText);

                tempEntry = new EntryInfo();
                tempEntry.entryID = entryID;
                tempEntry.entryType = 0;
                tempEntry.display = displayName;
                tempEntry.parentID = appcodeID;

                TreeNode tempNode;
                if (showIDCB.Checked == true)
                    tempNode = new TreeNode(displayName + " | " + entryID);
                else
                    tempNode = new TreeNode(displayName);

                tempNode.ImageIndex = 0;

                tempNode.Tag = tempEntry;
                subNode.Nodes.Add(tempNode);

            }

            for (int j = 0; j <= fileNodes.Count - 1; j++)
            {
                string entryID = fileNodes.Item(j).SelectNodes("id").Item(0).InnerText;
                string displayName = SimpleBase64.Decode(fileNodes.Item(j).SelectNodes("display").Item(0).InnerText);
                string status;
                try
                {
                    status = fileNodes.Item(j).SelectNodes("status").Item(0).InnerText;
                }
                catch { status = ""; }

                tempEntry = new EntryInfo();
                tempEntry.entryID = entryID;
                tempEntry.entryType = 1;
                tempEntry.display = displayName;
                tempEntry.status = status;
                tempEntry.parentID = appcodeID;


                TreeNode tempNode;
                if (showIDCB.Checked == true)
                    tempNode = new TreeNode(displayName + " | " + entryID);
                else
                    tempNode = new TreeNode(displayName);

                tempNode.ImageIndex = 1;
                tempNode.Tag = tempEntry;
                subNode.Nodes.Add(tempNode);
            }

            Cursor.Current = Cursors.Default;
        }

        private void folderTreeView_Click(object sender, EventArgs e)
        {

        }

        private void UploadFileToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                if (folderTreeView.SelectedNode != null)
                {
                    EntryInfo selectedEntry = (EntryInfo)folderTreeView.SelectedNode.Tag;
                    if (selectedEntry.entryType == 1)
                    {
                    }
                    else
                    {

                        OpenFileDialog ofd;

                        ofd = new OpenFileDialog();

                        if (ofd.ShowDialog() == DialogResult.OK)
                        {
                            Cursor.Current = Cursors.WaitCursor;
                            folderTreeView.SelectedNode.Nodes.Clear();

                            if (tokenString == "")
                            {
                                acquireToken();
                                if (tokenString == "") return;
                            }
                            if (etagString == "")
                                etagString = "system.new";

                            webUpload webupload = new webUpload();

                            webupload.tokenString = tokenString;
                            webupload.fileName = ofd.FileName;
                            webupload.parentFolderID = selectedEntry.entryID;
                            webupload.progressValue = 0;
                            webupload.webRelayURL = webRelayURL;
                            string entryID = webupload.uploadAFile();
                            string displayName = Path.GetFileName(ofd.FileName);

                            EntryInfo tempEntry = new EntryInfo();
                            tempEntry.entryID = entryID;
                            tempEntry.entryType = 1;
                            tempEntry.display = displayName;

                            TreeNode tempNode;
                            if (showIDCB.Checked == true)
                                tempNode = new TreeNode(displayName + "|" + entryID);
                            else
                                tempNode = new TreeNode(displayName);

                            tempNode.ImageIndex = 1;
                            tempNode.Tag = tempEntry;
                            folderTreeView.SelectedNode.Nodes.Add(tempNode);

                            Cursor.Current = Cursors.Default;
                        }
                    }
                }
            }
            catch { }
        }

        private void folderTreeView_AfterSelect(object sender, TreeViewEventArgs e)
        {
            if (folderTreeView.SelectedNode != null)
            {
                EntryInfo selectedEntry = (EntryInfo)folderTreeView.SelectedNode.Tag;
                if (selectedEntry == null)
                    folderTreeView.SelectedNode.ImageIndex = 0;
                else
                {
                    if (selectedEntry.entryType == 1)
                    {
                        folderTreeView.SelectedImageIndex = 1;
                    }
                    else
                    {
                        folderTreeView.SelectedImageIndex = 0;
                    }
                }
            }
        }

        private void createAFolderToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (folderTreeView.SelectedNode != null)
            {
                EntryInfo selectedEntry = (EntryInfo)folderTreeView.SelectedNode.Tag;
                if (selectedEntry.entryType == 1)
                {
                    return;
                }
                else
                {
                    string newFolderDisplay = "New Folder " + DateTime.Now;
                    string newFolderID = createFolder(selectedEntry.entryID, newFolderDisplay);
                    

                    EntryInfo tempEntry = new EntryInfo();
                    tempEntry.entryID = newFolderID;
                    tempEntry.entryType = 0;
                    tempEntry.display = newFolderDisplay;

                    TreeNode tempNode;
                    if (showIDCB.Checked == true)
                        tempNode = new TreeNode(newFolderDisplay + "|" + newFolderID);
                    else
                        tempNode = new TreeNode(newFolderDisplay);

                    tempNode.ImageIndex = 0;

                    tempNode.Tag = tempEntry;
                    folderTreeView.SelectedNode.Nodes.Add(tempNode);
                }
            }
        }

        private void transcodeToFLVToolStripMenuItem_Click(object sender, EventArgs e)
        {
            panelTranscode.Visible = true;
                
        }

        private void button1_Click(object sender, EventArgs e)
        {

            try
            {
                if (folderTreeView.SelectedNode != null)
                {
                    EntryInfo selectedEntry = (EntryInfo)folderTreeView.SelectedNode.Tag;
                    if (selectedEntry.entryType == 0)
                    {
                    }
                    else
                    {

                        Cursor.Current = Cursors.WaitCursor;

                        if (tokenString == "")
                        {
                            acquireToken();
                            if (tokenString == "") return;
                        }
                        if (etagString == "")
                            etagString = "system.new";

                        webUpload webupload = new webUpload();

                        string tempFileName = extTB.Text;
                        if (File.Exists(tempFileName) == false)
                        {
                            FileStream fileStream = new FileStream(tempFileName, FileMode.Create, FileAccess.ReadWrite);
                            fileStream.Close();
                        }

                        webupload.tokenString = tokenString;
                        webupload.fileName = tempFileName;
                        webupload.parentFolderID = selectedEntry.parentID;
                        webupload.progressValue = 0;
                        webupload.webRelayURL = webRelayURL;
                        string entryID = webupload.uploadAFile();
                        string displayName = tempFileName;
                        //File.Delete("blankfile.tmp");

                        EntryInfo tempEntry = new EntryInfo();
                        tempEntry.entryID = entryID;
                        tempEntry.entryType = 1;
                        tempEntry.display = displayName;

                        TreeNode tempNode;
                        if (showIDCB.Checked == true)
                            tempNode = new TreeNode(displayName + "|" + entryID);
                        else
                            tempNode = new TreeNode(displayName);

                        tempNode.ImageIndex = 1;
                        tempNode.Tag = tempEntry;
                        folderTreeView.SelectedNode.Nodes.Add(tempNode);


                        XmlDocument ydom = new XmlDocument();
                        XmlNodeList xNodes;
                        string mXML;
                        string responseXML;
                        mXML = "<submitjob><queue>ffmpegVideoConvert</queue><messagecontent>#&#xD;#Thu Mar 24 16:24:48 CST 2011&#xD;userid=" + userID + "&#xD;args=" + argTB.Text + "&#xD;srcfileid=" + selectedEntry.entryID + "&#xD;dstfileid=" + entryID + "&#xD;</messagecontent></submitjob>";
                        responseXML = postData(jobRelayURL + "/jobrelay/submitjob/" + tokenString, mXML);
                        MessageBox.Show(responseXML);
                        if (responseXML.IndexOf("<status>2</status>") != -1)
                        {
                            tokenString = "";
                            etagString = "";
                            ydom = null;

                        }

                        ydom.LoadXml(responseXML);
                        xNodes = ydom.SelectNodes("submitjob");
                        jobUUIDTB.Text = xNodes.Item(0).SelectNodes("jobuuid").Item(0).InnerText;
                        
                        Cursor.Current = Cursors.Default;

                    }
                }
            }
            catch (Exception ex) { }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            panelTranscode.Visible = false;
        }

        private void button3_Click(object sender, EventArgs e)
        {
            XmlDocument ydom = new XmlDocument();
            XmlNodeList xNodes;
            string mXML;
            string responseXML;
            mXML = "<jobstatus><queue>ffmpegVideoConvert</queue><jobuuid>" + jobUUIDTB.Text + "</jobuuid></jobstatus>";
            responseXML = postData(jobRelayURL + "/jobrelay/jobstatus/" + tokenString, mXML);
            //MessageBox.Show(responseXML);
            if (responseXML.IndexOf("<status>2</status>") != -1)
            {
                tokenString = "";
                etagString = "";
                ydom = null;

            }

            ydom.LoadXml(responseXML);
            xNodes = ydom.SelectNodes("jobstatus");
            string jobstatus = xNodes.Item(0).SelectNodes("jobstatus").Item(0).InnerText;
            if (jobstatus == "0") jobstatus = "Missing jobuuid";
            if (jobstatus == "16") jobstatus = "Already put in AMQ";
            if (jobstatus == "48") jobstatus = "Already put into JobTracker";
            if (jobstatus == "80") jobstatus = "Successfully complete";
            if (jobstatus == "112") jobstatus = "Job fail";
            string jobcompletetime = "";
            try
            {
                jobcompletetime = xNodes.Item(0).SelectNodes("completetime").Item(0).InnerText;
            }
            catch { }

            MessageBox.Show(jobstatus + "\n complete time = " + jobcompletetime + "\n" + responseXML);
        }

        private void button4_Click(object sender, EventArgs e)
        {
            if (userIDTB.Text == "" || userPasswordTB.Text == "")
            {
                MessageBox.Show("Please input valid WebStorageID and Password!");
                return;
            }
            userID = userIDTB.Text;
            userPassword = userPasswordTB.Text;

            if (txtProgKey.Text.Trim().Length > 0)
                stringProgKey = txtProgKey.Text.Trim();

            if (sidTB.Text.Trim().Length > 0)
                sequenceID = sidTB.Text.Trim();

            Cursor.Current = Cursors.WaitCursor;
            acquireToken();
            if (tokenString == "")
            {
                MessageBox.Show("Authentication error!");
                return;
            }
            else
                MessageBox.Show("Login successfully!");
        
            if (etagString == "")
                etagString = "system.new";

            getAccountInfo(userID, tokenString, serviceGatewayURL);
            MessageBox.Show("Get account info ok!");

            Cursor.Current = Cursors.Default;
        }

        public void getAccountInfo(string usr, string token, string sgURL)
        {
            string mXML;
            string responseXML;

            XmlDocument ydom = new XmlDocument();
            XmlNodeList packageNodes;
            XmlNodeList xNodes;

            try
            {
                if (sgURL == "") return;
                mXML = "<getinfo><token>" + token + "</token><userid>" + usr + "</userid><time>2008/1/1</time></getinfo>";
                responseXML = postData(sgURL + "/member/getinfo/", mXML);

                if (responseXML.IndexOf("<status>999</status>") != -1)
                {
                    return;
                }

                ydom.LoadXml(responseXML);
                xNodes = ydom.SelectNodes("getinfo");
                

                packageNodes = xNodes.Item(0).SelectNodes("package");
                usedSpaceTB.Text = xNodes.Item(0).SelectNodes("usedcapacity").Item(0).InnerText;
                packageSizeTB.Text = packageNodes.Item(0).SelectNodes("capacity").Item(0).InnerText;
                expireDateTB.Text = packageNodes.Item(0).SelectNodes("expire").Item(0).InnerText;

                ydom = null;
            }
            catch (Exception ew)
            {
                tokenString = "";
            }

        }

        private void btnBinUpload_Click(object sender, EventArgs e)
        {
            if (folderTreeView.Nodes == null || folderTreeView.Nodes.Count == 0 || folderTreeView.SelectedNode == null )
            {
                MessageBox.Show("Have to select uploading folder, before uploading file!", "Warning", MessageBoxButtons.OK);
                return;
            }

            string parent = appcodeID;

            TreeNode tn = folderTreeView.SelectedNode;
            EntryInfo eInfo = (EntryInfo)tn.Tag;

            if (eInfo.entryType == 0)
                parent = eInfo.entryID;

            OpenFileDialog ofd = new OpenFileDialog();
            DialogResult dres = ofd.ShowDialog();
            if (dres != DialogResult.OK)
            {
                MessageBox.Show("Please choice a file to upload!", "Warning", MessageBoxButtons.OK);
                return;
            }

            string uploadFile = ofd.FileName;

            /* Fetching file information */
            FileInfo fi = new FileInfo(uploadFile);
            long fileSize = fi.Length;
            string fileName = Crypto.UrlEncodeUpperCase(SimpleBase64.Encode(fi.Name));
            string fileCT = Convert.ToString(Times.GetTimestamp(fi.CreationTime));
            string fileLA = Convert.ToString(Times.GetTimestamp(fi.LastAccessTime));
            string fileLW = Convert.ToString(Times.GetTimestamp(fi.LastWriteTime));
            
            /* Composing file times to be attribute */
            StringBuilder attribute = new StringBuilder();
            attribute.Append("<creationtime>").Append(fileCT).Append("</creationtime>")
                     .Append("<lastaccesstime>").Append(fileLA).Append("</lastaccesstime>")
                     .Append("<lastwritetime>").Append(fileLW).Append("</lastwritetime>");

            string at = Crypto.UrlEncodeUpperCase(attribute.ToString());

            /* Calculating file checksum by SHA-512 */
            HashAlgorithm sha512 = new SHA512Managed();
            FileStream file = new FileStream(uploadFile, FileMode.Open, FileAccess.Read);
            byte[] retVal = null;
            retVal = sha512.ComputeHash(file);
            string hashValue = BitConverter.ToString(retVal).Replace("-", "").ToLower();
            file.Close();

            /******** InitBinaryUpload  *******************/
            StringBuilder url = new StringBuilder();
            url.Append(webRelayURL).Append("/webrelay/initbinaryupload/")
               .Append("?tk=").Append(tokenString)
               .Append("&pa=").Append(parent)
               .Append("&na=").Append(fileName)
               .Append("&at=").Append(at)
               .Append("&fs=").Append(fileSize)
               .Append("&sg=").Append(hashValue)
               .Append("&sc=").Append(appcodeID)
               .Append("&dis=").Append(sequenceID)
               ;

            string transId = null;
            string offset = "0";
            string fileId = null;
            string lastChecksum = null;

            try
            {
                string rsp = postData(url.ToString(), "");
                XmlDocument doc = new XmlDocument();
                XmlNodeList xNodes;
                doc.LoadXml(rsp);
                xNodes = doc.SelectNodes("initbinaryupload");

                XmlNode xn = xNodes.Item(0);

                string status = xn.SelectNodes("status").Item(0).InnerText;
                if (status == "0")
                {
                    if (xn.SelectNodes("transid") != null && xn.SelectNodes("transid").Item(0) != null)
                        transId = xn.SelectNodes("transid").Item(0).InnerText;
                    if (xn.SelectNodes("offset") != null && xn.SelectNodes("offset").Item(0) != null)
                        offset = xn.SelectNodes("offset").Item(0).InnerText;
                    if (xn.SelectNodes("latestchecksum") != null && xn.SelectNodes("latestchecksum").Item(0) != null)
                        lastChecksum = xn.SelectNodes("latestchecksum").Item(0).InnerText;
                    if (xn.SelectNodes("fileid") != null && xn.SelectNodes("fileid").Item(0) != null)
                        fileId = xn.SelectNodes("fileid").Item(0).InnerText;
                }
                else
                {
                    MessageBox.Show("InitBinaryUpload fail, status(" + status + ")");
                    return;
                }
            }
            catch (Exception e0)
            {
                MessageBox.Show("InitBinaryUpload fail:" + e0.Message);
                return;
            }
            /******** InitBinaryUpload End *******************/

            if (fileId != null && fileId.Trim().Length > 0)
            {
                MessageBox.Show("File upload successful, FileId (" + fileId + ")");
                return;
            }

            /******** ResumeBinaryUpload  *******************/
            url.Remove(0, url.Length);
            url.Append(webRelayURL).Append("/webrelay/resumebinaryupload/")
               .Append("?tk=").Append(tokenString)
               .Append("&tx=").Append(transId)
               .Append("&dis=").Append(sequenceID)
               ;

            FileStream fs = new FileStream(uploadFile, FileMode.Open, FileAccess.Read);
            try
            {
                string rsp = postData(url.ToString(), fs, fileSize);
                XmlDocument doc = new XmlDocument();
                XmlNodeList xNodes;
                doc.LoadXml(rsp);
                xNodes = doc.SelectNodes("resumebinaryupload");

                XmlNode xn = xNodes.Item(0);

                string status = xn.SelectNodes("status").Item(0).InnerText;
                if (status != "0")
                {
                    MessageBox.Show("ResumeBinaryUpload fail, status(" + status + ")");
                    return;
                }
            }
            catch (Exception e1)
            {
                MessageBox.Show("ResumeBinaryUpload fail:" + e1.Message);
                return;
            }
            finally
            {
                fs.Close();
            }
            /******** ResumeBinaryUpload End  *******************/

            /******** FinishBinaryUpload  *******************/
            url.Remove(0, url.Length);
            url.Append(webRelayURL).Append("/webrelay/finishbinaryupload/")
               .Append("?tk=").Append(tokenString)
               .Append("&tx=").Append(transId)
               .Append("&dis=").Append(sequenceID);

            if (lastChecksum != null && lastChecksum.Trim().Length > 0)
                url.Append("&lg=").Append(lastChecksum);

            try
            {
                string rsp = postData(url.ToString(), "");
                XmlDocument doc = new XmlDocument();
                XmlNodeList xNodes;
                doc.LoadXml(rsp);
                xNodes = doc.SelectNodes("finishbinaryupload");

                XmlNode xn = xNodes.Item(0);

                string status = xn.SelectNodes("status").Item(0).InnerText;
                if (status == "0")
                {
                    if (xn.SelectNodes("fileid") != null && xn.SelectNodes("fileid").Item(0) != null)
                        fileId = xn.SelectNodes("fileid").Item(0).InnerText;

                    MessageBox.Show("File upload successful, FileId (" + fileId + ")");
                }
                else
                {
                    MessageBox.Show("FinishBinaryUpload fail, status(" + status + ")");
                    return;
                }
            }
            catch (Exception e2)
            {
                MessageBox.Show("FinishBinaryUpload fail:" + e2.Message);
                return;
            }
            /******** FinishBinaryUpload End  *******************/
        }
    }
}
