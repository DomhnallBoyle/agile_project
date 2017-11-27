using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for UserDashboard.xaml
    /// </summary>
    /// 


    /*
     * TODO: Get End Point to send List Items using Button
     * TODO: Once Backend is written get edit User Model
     * TODO: Load In a User Skill set from model 
     *
     */

    public partial class UserDashboard : Page
    {
        #region Private variables

        private ProjectClient _client;
        

        #endregion

        #region Public variables

        public List<ProjectPermissions> ProjectList { get; set; }

        public String UserLabel { get; set; }

        public ObservableCollection<String> AvailableSkills
        { get; set; }

        public ObservableCollection<String> MySelectedObjects
        { get; set; }

        #endregion



        public UserDashboard()
        {
            setupTestData();
            InitializeComponent();
            DataContext = this;

            User user = ((User)Application.Current.Properties["user"]);
            UserLabel = user.GetFullName();
            string profileImageFileName;
            if (user.ProfilePicture != null)
            {
                profileImageFileName = user.ProfilePicture;
            }
            else
            {
                profileImageFileName = Properties.Settings.Default.DefaultProfileImage;
            }
            
            string profileImagePath = Properties.Settings.Default.ProfileImageDirectory + profileImageFileName;
            ProfilePicture.Source = new BitmapImage(new Uri(profileImagePath, UriKind.RelativeOrAbsolute));

            _client = new ProjectClient();

            this.AvailableSkills = new ObservableCollection<String>() { "Unix","Java","C#","VB","Matlab","Python"
            ,"Unix","Java","C#","VB","Matlab","Python"};
            this.MySelectedObjects =  new ObservableCollection<String>(setupTestData());
            InitialiseProjects();

        }

        #region Class methods

        private void InitialiseProjects()
        {
            try
            {
                List<Project> inboundProjects = _client.GetProjectsForUser(((User)Application.Current.Properties["user"]).Id);
                List<ProjectPermissions> projectList = new List<ProjectPermissions>();

                foreach (Project project in inboundProjects)
                {
                    projectList.Add(new ProjectPermissions(project, (User)Application.Current.Properties["user"]));
                }

                ProjectList = projectList;
            }
            catch (RestResponseErrorException ex)
            {
                if (ex.StatusCode == System.Net.HttpStatusCode.NotFound)
                {
                    MessageBoxUtil.ShowInfoBox(ex.Message);
                }
                else
                {
                    MessageBoxUtil.ShowErrorBox(ex.Message);
                }
            }
        }

    

   

        #endregion

        #region Command and Event methods

        private void ProjectListBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            Project selectedProject = ((ProjectPermissions)ProjectListBox.SelectedItem).Project;
            Page projectDashboardPage = new ProjectDashboard(selectedProject);

            NavigationService.GetNavigationService(this).Navigate(projectDashboardPage);
        }

        public ICommand NavigateToCreateProjectCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page createProjectPage = new CreateProject();

                    NavigationService.GetNavigationService(this).Navigate(createProjectPage);
                });
            }
        }

        public ICommand LogoutCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    if (Application.Current.Properties.Contains("user"))
                    {
                        Application.Current.Properties.Remove("user");
                    }

                    Page loginPage = new Login();

                    NavigationService.GetNavigationService(this).Navigate(loginPage);
                });
            }
        }
        public List<String> setupTestData()
        {
            List<string> list = new List<string> { "Python" };
            return list;
        }

        public ICommand UpdateSkillsCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Console.WriteLine("CPD");
                    List<String> myList = new List<String>(MySelectedObjects);
                    for (int i = 0; i < myList.Count; i++)
                    {
                        Console.WriteLine(myList[i]);
                    }

                });
            }
        }

        #endregion
    }
}
