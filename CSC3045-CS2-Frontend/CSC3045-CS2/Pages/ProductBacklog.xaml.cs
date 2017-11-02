using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Pages;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
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

namespace CSC3045_CS2
{
    /// <summary>
    /// Interaction logic for ProductBacklog.xaml
    /// </summary>
    public partial class ProductBacklog : Page
    {
        #region Private Variables

        private UserStoryClient _client;

        private ObservableCollection<UserStory> _backlog = new ObservableCollection<UserStory>();

        private Project _currentProject;

        #endregion

        #region Public Variables

        public string PageLabel { get; set; } = "Product Backlog";

        public string MarketValueLabel { get; set; } = "Market Value";
        
        public string BackButtonLabel { get; set; } = "Back";

        public string CreateStoryButtonLabel { get; set; } = "Create Story";

        #endregion

        public ProductBacklog(Project project)
        {
            InitializeComponent();
            DataContext = this;
            _client = new UserStoryClient();

            _currentProject = project;

            try
            {
                _backlog = _client.GetUserStories(project.Id);
            }
            catch (RestResponseErrorException ex)
            {
                MessageBox.Show(ex.Message);
            }

            Style itemContainerStyle = new Style(typeof(ListBoxItem));
            itemContainerStyle.Setters.Add(new Setter(ListBoxItem.AllowDropProperty, true));
            itemContainerStyle.Setters.Add(new EventSetter(ListBoxItem.PreviewMouseLeftButtonDownEvent, new MouseButtonEventHandler(MouseLeftDown)));
            itemContainerStyle.Setters.Add(new EventSetter(ListBoxItem.DropEvent, new DragEventHandler(StoryListDrop)));
            StoryList.ItemContainerStyle = itemContainerStyle;
            StoryList.ItemsSource = _backlog;
        }

        #region commands

        /// <summary>
        /// Returns to the project dashboard
        /// </summary>
        public ICommand BackCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page userDashboard = new UserDashboard();

                    NavigationService.GetNavigationService(this).Navigate(userDashboard);
                });
            }
        }

        /// <summary>
        /// Navigates to the Story creation page
        /// </summary>
        public ICommand CreateStoryCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page createUserStoryPage = new CreateUserStory(_currentProject);

                    NavigationService.GetNavigationService(this).Navigate(createUserStoryPage);
                });
            }
        }

        #endregion

        #region drag & drop functionality

        /// <summary>
        /// Method to run on mouse left click
        /// Checks to see if a list box item was clicked 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        public void MouseLeftDown(object sender, MouseButtonEventArgs e)
        {
            if (sender is ListBoxItem)
            {
                ListBoxItem draggedItem = sender as ListBoxItem;
                DragDrop.DoDragDrop(draggedItem, draggedItem.DataContext, DragDropEffects.Move);
                draggedItem.IsSelected = true;
            }
        }

        /// <summary>
        /// Function to run on dropping a Story list after dragging
        /// Gets index of dropped Story and target story, 
        /// and switches their positions in the observable list
        /// </summary>
        /// <param name="sender">Target user story</param>
        /// <param name="e">Dropped user story</param>
        public void StoryListDrop(object sender, DragEventArgs e)
        {
            UserStory droppedData = e.Data.GetData(typeof(UserStory)) as UserStory;
            ListBoxItem targetItem = ((ListBoxItem)sender);
            UserStory target = ((UserStory)targetItem.Content);

            int removedIdx = StoryList.Items.IndexOf(droppedData);
            int targetIdx = StoryList.Items.IndexOf(target);

            if (removedIdx < targetIdx)
            {
                _backlog.Insert(targetIdx + 1, droppedData);
                _backlog.RemoveAt(removedIdx);
            }
            else
            {
                int remIdx = removedIdx + 1;
                if (_backlog.Count + 1 > remIdx)
                {
                    _backlog.Insert(targetIdx, droppedData);
                    _backlog.RemoveAt(remIdx);
                }
            }
        }

        #endregion drag & drop functionality
    }


}
