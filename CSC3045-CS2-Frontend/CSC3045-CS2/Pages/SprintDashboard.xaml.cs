using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Collections.Generic;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using CSC3045_CS2.Exception;


namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for SprintDashboard.xaml
    /// </summary>
    public partial class SprintDashboard : BasePage, INotifyPropertyChanged
    {
        #region Private variables

        private SprintClient _sprintClient;
        private TaskClient _taskClient;
        private Boolean _fromFile;
        private List<Task> _tasks { get; set; }

        #endregion

        #region Public variables

        public String SprintDetails { get; set; }
        public Sprint CurrentSprint { get; set; }
        public Task CurrentTask { get; set; }
        public List<UserStory> UserStories { get; set; }
        public Permissions Permissions { get; set; }
        public List<Task> Tasks
        {
            get { return _tasks; }
            set
            {
                _tasks = value;
                OnPropertyChanged();
            }
        }

        #endregion

        public SprintDashboard(Sprint sprint, bool fromFile)
        {
            InitializeComponent();
            CurrentPage = this.Title;
            this._fromFile = fromFile;
            DataContext = this;

            _sprintClient = new SprintClient();
            _taskClient = new TaskClient();

            Permissions = new Permissions((User)Application.Current.Properties["user"], sprint.Project);
         
            sprint.Users = _sprintClient.GetSprintTeam(sprint.Project.Id, sprint.Id);
            UserStories = _sprintClient.GetSprintStories(sprint.Project.Id, sprint.Id);

            CurrentSprint = sprint;

            SprintDetails = string.Format("Name: {0}, Start Date: {1}, End Date: {2}", CurrentSprint.Name, CurrentSprint.StartDate, CurrentSprint.EndDate); 
        }

        /// <summary>
        /// Double Click event for mouse
        /// Displays tasks from clicked user story
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        public void ListBox_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            try
            {
                UserStory userStory = (UserStory)(sender as ListBoxItem).DataContext;
                List<Task> _tasks = _taskClient.GetTasksByUserStoryId(userStory.Project.Id, userStory.Id);
                Tasks = _tasks;
            }
            catch (RestResponseErrorException ex)
            {
                MessageBoxUtil.ShowErrorBox(ex.Message);
            }
        }

        #region Command and Event methods

        /// <summary>
        /// Relay command for navigating to the edit sprint team page
        /// </summary>
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

        /// <summary>
        /// Relay command for navigating to the edit sprint backlog page
        /// </summary>
        public ICommand NavigateToManageSprintBacklogCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page editSprintBacklog = new EditSprintBacklog(CurrentSprint);

                    NavigationService.GetNavigationService(this).Navigate(editSprintBacklog);
                });
            }
        }

        /// <summary>
        /// Returns user to the previous page
        /// </summary>
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

        /// <summary>
        /// Assigns user to the task
        /// </summary>
        public ICommand AssignCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    CurrentTask = (Task)param;
                    CurrentTask.Assignee = ((User)Application.Current.Properties["user"]);
                    try
                    {
                        CurrentTask =_taskClient.UpdateTask(CurrentTask.UserStory.Project.Id, CurrentTask.UserStory.Id, CurrentTask);

                        MessageBoxUtil.ShowSuccessBox("Succesfully Updated");
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBoxUtil.ShowErrorBox(ex.Message);
                    }
                });
            }
        }

        /// <summary>
        /// Brings user to the manage tasks screen to add more tasks to user story
        /// </summary>
        public ICommand CreateTaskCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    UserStory userStory = (UserStory)param;
                    Page manageTaskPage = new CreateTask(userStory, CurrentSprint.Id);

                    NavigationService.GetNavigationService(this).Navigate(manageTaskPage);
                });
            }
        }

        /// <summary>
        /// Method to alert UI of property changed
        /// </summary>
        /// <param name="propertyName"></param>
        private void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        public event PropertyChangedEventHandler PropertyChanged;

        #endregion

    }
}
