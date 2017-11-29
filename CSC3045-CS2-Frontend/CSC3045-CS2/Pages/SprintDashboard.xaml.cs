using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using CSC3045_CS2.Pages;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.ComponentModel;
using System.Runtime.CompilerServices;


namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for SprintDashboard.xaml
    /// </summary>
    public partial class SprintDashboard : BasePage, INotifyPropertyChanged
    {
        #region Private variables

        private SprintClient _client;
        private TaskClient _taskClient;
        private Boolean _fromFile;

        #endregion

        #region Public variables

        public Sprint CurrentSprint { get; set; }

        private List<Task>_tasks { get; set; }
        public List<UserStory> UserStories { get; set; }
        public Permissions Permissions { get; set; }

        #endregion

        public SprintDashboard(Sprint sprint, bool fromFile)
        {
            InitializeComponent();
            CurrentPage = this.Title;
            this._fromFile = fromFile;
            DataContext = this;

            _client = new SprintClient();
            _taskClient = new TaskClient();


            Permissions = new Permissions((User)Application.Current.Properties["user"], sprint.Project);
         
            sprint.Users = _client.GetSprintTeam(sprint.Project.Id, sprint.Id);
            UserStories = _client.GetSprintStories(sprint.Id);

            CurrentSprint = sprint;
            
        }
        public void ListBox_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
                UserStory userStory = (UserStory)(sender as ListBoxItem).DataContext;
                List<Task> _tasks = _taskClient.GetTasksByUserStoryId(userStory.Project.Id, userStory.Id);
                Tasks = _tasks;
                Console.WriteLine(_tasks);

            
        }
        public List<Task> Tasks
        {
            get { return _tasks; }
            set
            {
                _tasks = value;
                OnPropertyChanged();
            }
        }
        private void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        public event PropertyChangedEventHandler PropertyChanged;

        #region Command and Event methods


        public ICommand NavigateToManageSprintsCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page editSprintTeam = new EditSprintTeam(CurrentSprint);

                    NavigationService.GetNavigationService(this).Navigate(editSprintTeam);
                });
            }
        }

        public ICommand BackCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page manageSprintsPage = new ManageSprints(CurrentSprint.Project);

                    NavigationService.GetNavigationService(this).Navigate(manageSprintsPage);

                });
            }
        }

        public ICommand TaskCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    UserStory userStory = new UserStoryClient().GetUserStory(1);
                    Page manageTaskPage = new ManageTasks(userStory, CurrentSprint.Id);

                    NavigationService.GetNavigationService(this).Navigate(manageTaskPage);
                });
            }
        }

        #endregion
    }
}
